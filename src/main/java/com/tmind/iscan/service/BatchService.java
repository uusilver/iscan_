package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_ADVICE_TEMPLATE;
import com.tmind.iscan.entity.M_USER_CATEGORY_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
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
}
