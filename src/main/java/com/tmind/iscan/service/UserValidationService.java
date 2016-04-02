package com.tmind.iscan.service;

import com.tmind.iscan.entity.AgentUser;
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

    public AgentUser findAgentUserInDatabase(String username, String password){

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AgentUser> list = null;
        try{
            Query q = session.createSQLQuery("select * from agency_user where username = :username and password = :password").addEntity(AgentUser.class);
            q.setString("username",username);
            q.setString("password", SecurityUtil.encodeWithMd5Hash(password));
            list = q.list();
            log.info("用户登陆成功:"+username);
            return  ((AgentUser)list.get(0));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return null;
    }

    public UserEntity findUserEntity(String username, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserEntity entity = null;
        try{
            Query q = session.createSQLQuery("select * from User where username = :username and password = :password").addEntity(UserEntity.class);
            q.setString("username",username);
            q.setString("password", SecurityUtil.encodeWithMd5Hash(password));
            if(q.list().size()>0){
                entity = (UserEntity)q.list().get(0);
                log.info("用户登陆成功:"+username);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return entity;
    }

    public UserEntity findUserEntityByUsername(String username, String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserEntity entity = null;
        try{
            Query q = session.createSQLQuery("select * from User where username = :username and user_email = :email").addEntity(UserEntity.class);
            q.setString("username",username);
            q.setString("email", email);
            entity = (UserEntity)q.list().get(0);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return entity;
    }
}
