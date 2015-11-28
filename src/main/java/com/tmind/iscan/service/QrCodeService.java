package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.entity.M_USER_QRCODE_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
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
        try {
            String hql = "from M_USER_QRCODE_ENTITY as M_USER_QRCODE_ENTITY where M_USER_QRCODE_ENTITY.user_id=:userId and M_USER_QRCODE_ENTITY.product_id=:productId and M_USER_QRCODE_ENTITY.product_batch=:batchId";//使用命名参数，推荐使用，易读。
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
