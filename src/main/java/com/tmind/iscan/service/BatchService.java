package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_ADVICE_TEMPLATE;
import com.tmind.iscan.entity.M_USER_CATEGORY_ENTITY;
import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/28.
 */
@SuppressWarnings("ALL")
@Service("batchService")
public class BatchService {

    public List<M_USER_ADVICE_TEMPLATE> queryBatch(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_ADVICE_TEMPLATE> list = null;
        try{
            String hql = "from M_USER_ADVICE_TEMPLATE as M_USER_ADVICE_TEMPLATE where M_USER_ADVICE_TEMPLATE.user_id=:userId";
            Query query = session.createQuery(hql);
            query.setInteger("userId",userId);
            list = query.list();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    //查询产品信息

    public List<M_USER_PRODUCT_ENTITY> queryProductInfo(Integer userId, String searchType, String searchContent){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PRODUCT_ENTITY> list = null;
        try {
            String hql = "";
            Query query = null;
            if(searchType.equalsIgnoreCase("productId")&&searchContent.length()>0){
                hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id like :product_id";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
                query.setString("product_id","%"+searchContent+"%");

            }else if(searchType.equalsIgnoreCase("batchNo")&&searchContent.length()>0){
                hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.relate_batch like :batchNo";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
                query.setString("batchNo","%"+searchContent+"%");
            }else if(searchContent.length()==0){
                hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId order by M_USER_PRODUCT_ENTITY.product_name";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
            }

            list = query.list();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    public boolean deleteQrCodes(Integer userId, String productId, String batchNo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            String hql = "delete M_USER_QRCODE_ENTITY as M_USER_QRCODE_ENTITY where M_USER_QRCODE_ENTITY.user_id=:userId and M_USER_QRCODE_ENTITY.product_id=:productId and M_USER_QRCODE_ENTITY.product_batch=:batchNo";
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.setString("batchNo",batchNo);
            query.executeUpdate();
            trans.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }
}
