package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_PRODUCT_SHOW_INFO;
import com.tmind.iscan.entity.System_meta_Info;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

/**
 * Created by lijunying on 15/12/7.
 */

@SuppressWarnings("ALL")
@Service("productInfoService")
public class ProductInfoService {

    public boolean createProductInfo(M_PRODUCT_SHOW_INFO infoEntity){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction tran=session.beginTransaction();
            String hql = "delete M_PRODUCT_SHOW_INFO as M_PRODUCT_SHOW_INFO where M_PRODUCT_SHOW_INFO.user_id=:userId and M_PRODUCT_SHOW_INFO.product_id=:productId and M_PRODUCT_SHOW_INFO.batch_no=:batchNo";
            Query query = session.createQuery(hql);
            query.setInteger("userId", infoEntity.getUser_id());
            query.setString("productId",infoEntity.getProduct_id());
            query.setString("batchNo",infoEntity.getBatch_no());
            query.executeUpdate();
            session.save(infoEntity);
            tran.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally{
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public M_PRODUCT_SHOW_INFO queryInfoBySpecificInfo(String productId, String batchNo, Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        M_PRODUCT_SHOW_INFO info = null;
        try{
            String hql = "from M_PRODUCT_SHOW_INFO as M_PRODUCT_SHOW_INFO where M_PRODUCT_SHOW_INFO.user_id=:userId and M_PRODUCT_SHOW_INFO.product_id=:productId and M_PRODUCT_SHOW_INFO.batch_no=:batchNo";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId",productId);
            query.setString("batchNo",batchNo);
            if (query.list().size()>0)
                    info = (M_PRODUCT_SHOW_INFO)query.list().get(0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally
        {
            if(session!=null){
                session.close();
            }
        }
        return info;
    }
}
