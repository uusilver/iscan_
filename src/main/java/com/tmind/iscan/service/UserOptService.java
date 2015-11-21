package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

/**
 * Created by lijunying on 15/11/21.
 */
@Service("userOptService")
public class UserOptService {

    public boolean createUserProducet(M_USER_PRODUCT_ENTITY productEntity){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran=session.beginTransaction();
        session.save(productEntity);
        tran.commit();
        session.close();
        return true;
    }
}
