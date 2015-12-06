package com.tmind.iscan.service;

import com.tmind.iscan.entity.UserEntity;
import com.tmind.iscan.util.HibernateUtil;
import com.tmind.iscan.util.SecurityUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/13.
 */
@Service("userValService")
public class UserValidationService {

    Logger log = Logger.getLogger(UserValidationService.class);

    public Integer findUserInDatabase(String username, String password){

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserEntity> list = null;
        Integer id = 0;
        try{
            Query q = session.createSQLQuery("select * from User where username = :username and password = :password").addEntity(UserEntity.class);
            q.setString("username",username);
            q.setString("password", SecurityUtil.encodeWithMd5Hash(password));
            list = q.list();
            log.info("用户登陆成功:"+username);
            id = list.get(0).getId();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return id;
    }

    public String findUserQrTableFromDatabase(String username, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserEntity> list = null;
        try{
            Query q = session.createSQLQuery("select * from User where username = :username and password = :password").addEntity(UserEntity.class);
            q.setString("username",username);
            q.setString("password", SecurityUtil.encodeWithMd5Hash(password));
            list = q.list();
            log.info("用户登陆成功:"+username);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list.get(0).getQuery_qrcode_table();
    }
}
