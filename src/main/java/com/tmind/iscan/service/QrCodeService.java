package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.entity.M_USER_QRCODE_ENTITY;
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
@Service("qrCodeService")
public class QrCodeService {

    public List<M_USER_QRCODE_ENTITY> queryQrCode(Integer userId, String procutId, String batchId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_QRCODE_ENTITY> list = null;
        Transaction trans = session.beginTransaction();
        try {
            String hql = "from M_USER_QRCODE_ENTITY as M_USER_QRCODE_ENTITY where M_USER_QRCODE_ENTITY.user_id=:userId and M_USER_QRCODE_ENTITY.product_id=:productId and M_USER_QRCODE_ENTITY.product_batch=:batchId and M_USER_QRCODE_ENTITY.active_flag='Y'";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId",procutId);
            query.setString("batchId",batchId);
            list = query.list();
            String updateSql = "update  M_USER_QRCODE_ENTITY m set m.active_flag='N' where m.user_id=:userId and m.product_id=:productId";
            query = session.createQuery(updateSql);
            query.setInteger("userId", userId);
            query.setString("productId",procutId);
            query.executeUpdate();
            trans.commit();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    public List<M_USER_QRCODE_ENTITY> queryQrCodeForReExport(Integer userId, String procutId, String batchId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_QRCODE_ENTITY> list = null;
        try {
            String hql = "from M_USER_QRCODE_ENTITY as M_USER_QRCODE_ENTITY where M_USER_QRCODE_ENTITY.user_id=:userId and M_USER_QRCODE_ENTITY.product_id=:productId and M_USER_QRCODE_ENTITY.product_batch=:batchId and M_USER_QRCODE_ENTITY.active_flag='N'";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId",procutId);
            query.setString("batchId",batchId);
            list = query.list();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

}
