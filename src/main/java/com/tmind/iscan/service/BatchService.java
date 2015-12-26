package com.tmind.iscan.service;


import com.tmind.iscan.entity.M_USER_ADVICE_TEMPLATE;
import com.tmind.iscan.entity.M_USER_CATEGORY_ENTITY;
import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.entity.M_USER_PRODUCT_META;
import com.tmind.iscan.model.BatchQueryTo;
import com.tmind.iscan.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunying on 15/11/28.
 */
@SuppressWarnings("ALL")
@Service("batchService")
public class BatchService {

    Log log = LogFactory.getLog(BatchService.class);

    @Resource(name="productService")
    private  ProductService productService;

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

    public List<BatchQueryTo> queryProductInfo(Integer userId, String searchType, String searchContent, Integer firstRecord, Integer maxResult){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BatchQueryTo> list = new ArrayList<BatchQueryTo>();
        List<M_USER_PRODUCT_ENTITY> batchList = null;
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
                hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId order by M_USER_PRODUCT_ENTITY.product_id";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
            }

            query.setFirstResult(firstRecord);
            query.setMaxResults(maxResult);
            batchList = query.list();
            for(M_USER_PRODUCT_ENTITY m:batchList){
                BatchQueryTo b = new BatchQueryTo();
                M_USER_PRODUCT_META meta = productService.queryProductInfoById(userId,m.getProduct_id());
                b.setId(m.getId());
                b.setProductId(m.getProduct_id());
                b.setProductName(meta.getProduct_name());
                b.setAdviceTemplate(m.getAdvice_temp());
                b.setBatchNo(m.getRelate_batch());
                b.setQrTotalNo(m.getQrcode_total_no());
                b.setUpdateTime(m.getUpdate_time());
                b.setSellArthor(m.getSellArthor());
                //去掉json的包装符号
                b.setParams(m.getBatch_params().replaceAll("\\[","").replaceAll("\\]", "").replaceAll("\"",""));
                list.add(b);
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    public Integer getProductBatchTotalNo(Integer userId, String searchType, String searchContent){
        Session session = HibernateUtil.getSessionFactory().openSession();

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
                hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId order by M_USER_PRODUCT_ENTITY.product_id";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
            }

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
            hql = "delete M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id=:productId and M_USER_PRODUCT_ENTITY.relate_batch=:batchNo";
            query = session.createQuery(hql);
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

    //根据ID查产品批次

    public boolean queryBatchInfoByProductId(Integer userId, String productId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean flag = false;
        try {
            String hql = "";
            Query query = null;
                hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id =:productId";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
                query.setString("productId",productId);
            if(query.list().size()>0){
                flag = true;
            }
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return flag;
    }
}
