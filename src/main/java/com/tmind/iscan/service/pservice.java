package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_CATEGORY_ENTITY;
import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.entity.M_USER_PRODUCT_META;
import com.tmind.iscan.entity.M_USER_QRCODE_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/12/20.
 *
 * @DESC: 用来做性能测试的页面
 */
@Service("pservice")
public class pservice {

    //查询全产品时间
    public long getProductMetaQueryTime() {
        long startMils = System.currentTimeMillis();
        long endMils = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PRODUCT_META> list = null;
        try {
            String hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            endMils = System.currentTimeMillis();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return endMils-startMils;
    }

    //查询全批次时间
    public long getProductBatchQueryTime() {
        long startMils = System.currentTimeMillis();
        long endMils = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PRODUCT_ENTITY> list = null;
        try {
            String hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            endMils = System.currentTimeMillis();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return endMils-startMils;
    }

    //查询全类别时间
    public long getCategoryQueryTime() {
        long startMils = System.currentTimeMillis();
        long endMils = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_CATEGORY_ENTITY> list = null;
        try {
            String hql = "from M_USER_CATEGORY_ENTITY as M_USER_CATEGORY_ENTITY";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            endMils = System.currentTimeMillis();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return endMils-startMils;
    }

    //二维码扫码表查询时间
    public long getQrcodeQueryTime() {
        long startMils = System.currentTimeMillis();
        long endMils = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_QRCODE_ENTITY> list = null;
        try {
            String hql = "from M_USER_QRCODE_ENTITY as M_USER_QRCODE_ENTITY";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            endMils = System.currentTimeMillis();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return endMils-startMils;
    }
}
