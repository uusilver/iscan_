package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.entity.M_USER_PRODUCT_META;
import com.tmind.iscan.entity.M_USER_QRCODE_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lijunying on 15/11/21.
 */
@SuppressWarnings("ALL")
@Service("productService")
public class ProductService {

    //创建产品信息
    public boolean createUserProducet(M_USER_PRODUCT_META productMeta){
        Session session = HibernateUtil.getSessionFactory().openSession();
        productMeta.setAdvice_temp("default");
        try{
            Transaction tran=session.beginTransaction();
            session.save(productMeta);
            tran.commit();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    //查询产品信息

    public List<M_USER_PRODUCT_META> queryProductInfo(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PRODUCT_META> list = null;
        try {
            String hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId order by M_USER_PRODUCT_META.update_time desc";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            list = query.list();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    //删除产品信息
    public boolean deleteProduct(Integer userId, String productId, String batchNo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            String hql = "delete M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_id=:productId and M_USER_PRODUCT_META.relate_batch=:batchNo";
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.setString("batchNo", batchNo);

            int result  = query.executeUpdate();
            trans.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally{
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    //删除产品信息(仅根据产品id),同时清空product_meta表和m_user_product表
    public boolean deleteProduct4ProductId(Integer userId, String productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            String hql = "delete M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_id=:productId";
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            int result  = query.executeUpdate();
            hql = "delete M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id=:productId";
            query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            result  = query.executeUpdate();
            trans.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally{
            if (session != null) {
                session.close();
            }
        }
        return true;
    }


    //根据产品id查询
    public M_USER_PRODUCT_META queryProductInfoById(Integer userId, String productId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        M_USER_PRODUCT_META productMeta = null;
        try {
            String hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_id=:productId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            productMeta = (M_USER_PRODUCT_META)query.list().get(0);
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return productMeta;
    }

    //为当前的产品创建一个新的批次-->插入到m_user_product表中
    public boolean createNewProductBatch(M_USER_PRODUCT_ENTITY productEntity){
        Session session =null;
        try
        {
            session= HibernateUtil.getSessionFactory().openSession();
            //开启事务.
            Transaction tran = session.beginTransaction();
            session.save(productEntity);
            //提交事务.把内存的改变提交到数据库上.
            tran.commit();

        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    //更新产品
    public boolean updateProductById(M_USER_PRODUCT_META meta){
        Session session =null;
        try
        {
            session= HibernateUtil.getSessionFactory().openSession();
            //开启事务.
            Transaction tran = session.beginTransaction();
            session.update(meta);
            //提交事务.把内存的改变提交到数据库上.
            tran.commit();

        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public boolean createQrcode(M_USER_QRCODE_ENTITY qrcodeEntity){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction tran=session.beginTransaction();
            qrcodeEntity.setQuery_times(0);
            qrcodeEntity.setActive_flag("Y");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            qrcodeEntity.setCreate_date(sdf.format(new Date()));
            session.save(qrcodeEntity);
            tran.commit();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public boolean updateProductAndBatchQrTotalAccount(Integer userId, String productId, String batchId, Integer qrAccount){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran=session.beginTransaction();
        M_USER_PRODUCT_ENTITY m_user_product_entity = null;
        try {
            //更新二维码生成到批次数
            String hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id=:productId and M_USER_PRODUCT_ENTITY.relate_batch=:batchId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.setString("batchId", batchId);
            m_user_product_entity = (M_USER_PRODUCT_ENTITY)query.list().get(0);
            m_user_product_entity.setQrcode_total_no(m_user_product_entity.getQrcode_total_no() + qrAccount);
            session.update(m_user_product_entity);
            //更新二维码总数
            hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_id=:productId";//使用命名参数，推荐使用，易读。
            query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            M_USER_PRODUCT_META meta = null;
            meta = (M_USER_PRODUCT_META)query.list().get(0);
            meta.setQrcode_total_no(meta.getQrcode_total_no() + qrAccount);
            session.update(meta);
            tran.commit();
        }catch (Exception e){
            tran.rollback();
            return false;
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }
}
