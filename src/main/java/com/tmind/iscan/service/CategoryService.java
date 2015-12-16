package com.tmind.iscan.service;

import com.tmind.iscan.entity.M_USER_CATEGORY_ENTITY;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/22.
 */
@SuppressWarnings("ALL")
@Service("categoryService")
public class CategoryService {

    public boolean createCategory(M_USER_CATEGORY_ENTITY category){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            session.save(category);
            trans.commit();
            System.out.println("分类创建成功");
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public List<M_USER_CATEGORY_ENTITY> queryCategory(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_CATEGORY_ENTITY> list = null;
        try{
            String hql = "from M_USER_CATEGORY_ENTITY as M_USER_CATEGORY_ENTITY where M_USER_CATEGORY_ENTITY.user_id=:userId";
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

    public boolean deleteCategory(Integer categoryId, Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            String hql = "delete M_USER_CATEGORY_ENTITY as M_USER_CATEGORY_ENTITY where M_USER_CATEGORY_ENTITY.Id=:Id and M_USER_CATEGORY_ENTITY.user_id=:userId";
            Query query = session.createQuery(hql);
            query.setInteger("Id",categoryId);
            query.setInteger("userId",userId);
            query.executeUpdate();
            trans.commit();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public boolean checkCategoryUsedOrNot(Integer categoryId, Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        M_USER_CATEGORY_ENTITY categoryEntity = null;
        try{
            String hql = "from M_USER_CATEGORY_ENTITY as M_USER_CATEGORY_ENTITY where M_USER_CATEGORY_ENTITY.user_id=:userId and M_USER_CATEGORY_ENTITY.Id=:id";
            Query query = session.createQuery(hql);
            query.setInteger("userId",userId);
            query.setInteger("id",categoryId);
            categoryEntity = (M_USER_CATEGORY_ENTITY)query.list().get(0);
            hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_category=:product_category";
            query = session.createQuery(hql);
            query.setInteger("userId",userId);
            query.setString("product_category",categoryEntity.getCategory_name());
            if(query.list().size()>0)
                return false;
            else
                return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }
}
