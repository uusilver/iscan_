package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/21.
 */
@SuppressWarnings("ALL")
@Service("userOptService")
public class UserOptService {

    //创建产品信息
    public boolean createUserProducet(M_USER_PRODUCT_ENTITY productEntity){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction tran=session.beginTransaction();
            session.save(productEntity);
            tran.commit();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    //查询产品信息

    public List<M_USER_PRODUCT_ENTITY> queryProductInfo(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PRODUCT_ENTITY> list = null;
        try {
            String hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId";//使用命名参数，推荐使用，易读。
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
    public boolean deleteProduct(Integer userId, String productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "delete M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id=:productId";
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.executeUpdate();
            session.beginTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    //根据产品id查询
    public M_USER_PRODUCT_ENTITY queryProductInfoById(Integer userId, String productId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        M_USER_PRODUCT_ENTITY m_user_product_entity = null;
        try {
            String hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id=:productId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            m_user_product_entity = (M_USER_PRODUCT_ENTITY)query.list().get(0);
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return m_user_product_entity;
    }

    //更新操作
    public boolean updateProductById(M_USER_PRODUCT_ENTITY productEntity){
        Session session =null;
        try
        {
            session= HibernateUtil.getSessionFactory().openSession();
            //开启事务.
            Transaction tran = session.beginTransaction();
            session.update(productEntity);
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
}
