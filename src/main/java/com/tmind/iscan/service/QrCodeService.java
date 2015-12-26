package com.tmind.iscan.service;

import com.tmind.iscan.controller.QRcodeExportController;
import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.entity.M_USER_PRODUCT_META;
import com.tmind.iscan.entity.M_USER_QRCODE_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import com.tmind.iscan.util.IPAnalyzer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import sun.org.mozilla.javascript.internal.EcmaError;

import java.util.List;

/**
 * Created by lijunying on 15/11/28.
 */
@SuppressWarnings("ALL")
@Service("qrCodeService")
public class QrCodeService {

    Log log = LogFactory.getLog(QrCodeService.class);
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
        }catch (Exception e){
            log.warn(e.getMessage());
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
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    //查询二维码表中的IP地址，并且更新为相关的物理地址，过滤掉0.0.0.0的选项
    public void queryQrCode4UpdatePhysicalAddress(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_QRCODE_ENTITY> list = null;
        Transaction trans = session.beginTransaction();
        try {
            String hql = "from M_USER_QRCODE_ENTITY as M_USER_QRCODE_ENTITY where  M_USER_QRCODE_ENTITY.ip_check_flag='N'";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            for(M_USER_QRCODE_ENTITY qModel:list){
                String ipAddress = qModel.getVistor_ip_addr();
                //过滤掉本地地址
                if(!ipAddress.equals("0:0:0:0:0:0:0:1")&&ipAddress!=null&&!ipAddress.equals("")){
                    System.out.println("处理IP地址");
                    String physicalAddress = IPAnalyzer.queryAddressByIp(ipAddress);
                    //获得ip地址
                    if(physicalAddress!=null){
                        String updateSql = "update  M_USER_QRCODE_ENTITY m set m.ip_check_flag='Y', m.vistor_phy_addr =:phyAddr where m.Id=:id";
                        query = session.createQuery(updateSql);
                        query.setInteger("id", qModel.getId());
                        query.setString("phyAddr",physicalAddress);
                        query.executeUpdate();
                        trans.commit();
                    }
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally{
            if(session!=null){
                session.close();
            }
        }

    }
    //查询用户下全部的二维码 分页
    public List<M_USER_QRCODE_ENTITY> queryAllQrcodetInfo(Integer userId, Integer firstRecord, Integer maxResult){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_QRCODE_ENTITY> list = null;
        try {
            String hql = "from M_USER_QRCODE_ENTITY as M_USER_QRCODE_ENTITY where M_USER_QRCODE_ENTITY.user_id=:userId and M_USER_QRCODE_ENTITY.query_times>0";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
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

    //统计用户下全部二维码的总条数
    public Integer getQrcodeTotalNo(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(M_USER_QRCODE_ENTITY .id) from M_USER_QRCODE_ENTITY as M_USER_QRCODE_ENTITY where M_USER_QRCODE_ENTITY.user_id=:userId";//使用命名参数，推荐使用，易读。
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
}
