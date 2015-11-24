package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_ACCOUNT;
import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/24.
 */
@SuppressWarnings("ALL")
@Service("userAccountService")
public class UserAccountService {

    //查询用户余额是否能够满足打印标签的需求
    //创建产品信息
    public boolean judgeCanPrintQrCodeOrNot(Integer printQrcodeNo, Integer userId){
        boolean flag = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PRODUCT_ENTITY> list = null;
        try {
            String hql = "from M_USER_ACCOUNT as M_USER_ACCOUNT where M_USER_ACCOUNT.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            Integer qrCodeInAccount = ((M_USER_ACCOUNT)query.list().get(0)).getAccount();
            if(qrCodeInAccount<printQrcodeNo)
                flag =  false;
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return flag;
    }

    //更新用户账户
    public boolean updateUserAccountForConsuming(Integer printQrcodeNo, Integer userId){
        boolean flag = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PRODUCT_ENTITY> list = null;
        try {
            Transaction tran = session.beginTransaction();
            String hql = "from M_USER_ACCOUNT as M_USER_ACCOUNT where M_USER_ACCOUNT.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            M_USER_ACCOUNT userAccount = ((M_USER_ACCOUNT)query.list().get(0));
            userAccount.setAccount(userAccount.getAccount()-printQrcodeNo);
            session.update(userAccount);
            tran.commit();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return flag;
    }
}
