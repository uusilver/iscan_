package com.tmind.iscan.service;

import com.mysql.jdbc.log.LogFactory;
import com.tmind.iscan.entity.M_USER_ACCOUNT;
import com.tmind.iscan.entity.M_USER_PRODUCT_META;
import com.tmind.iscan.entity.UserEntity;
import com.tmind.iscan.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 16/1/14.
 */
@Service("agencyService")
public class AgencyService {

    Log log = org.apache.commons.logging.LogFactory.getLog(AgencyService.class);

    public List<UserEntity> queryUserInfo(Integer agency_id, Integer firstRecord, Integer maxResult){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserEntity> list = null;
        try {
            String hql = "from UserEntity as UserEntity where UserEntity.agency_id=:agency_id";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("agency_id", agency_id);
            query.setFirstResult(firstRecord);
            query.setMaxResults(maxResult);
            list = query.list();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    public Integer getProductMetaTotalNo(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(M_USER_PRODUCT_META.id) from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            return ((Number) query.iterate().next()).intValue();
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return 0;
    }

    public Integer getUserAccountNo(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from M_USER_ACCOUNT as M_USER_ACCOUNT where M_USER_ACCOUNT.user_id=:user_id";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("user_id", userId);
            return ((M_USER_ACCOUNT)query.list().get(0)).getAccount();
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return 0;
    }

    public boolean updateUser(UserEntity user){
        Session session =null;
        try
        {
            session= HibernateUtil.getSessionFactory().openSession();
            //开启事务.
            Transaction tran = session.beginTransaction();
            session.update(user);
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

    public UserEntity getUserById(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from UserEntity as UserEntity where UserEntity.id=:user_id";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("user_id", userId);
            return ((UserEntity)query.list().get(0));
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return null;
    }
}
