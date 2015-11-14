package com.tmind.iscan.service;

import com.tmind.iscan.entity.UserEntity;
import com.tmind.iscan.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/13.
 */
@Service("userValService")
public class UserValidationService {

    public boolean findUserInDatabase(String username, String password){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createSQLQuery("select * from User where username = :username and password = :password").addEntity(UserEntity.class);
        q.setString("username",username);
        q.setString("password",password);
        List<UserEntity> list = q.list();

//        用map来获得查询结果
//        Query q = session.createSQLQuery("select username from User where username = :username").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//        q.setString("username",user.getUsername());
//        Map map = (Map)q.list().get(0);
//        log.info("------++++"+String.valueOf(map.get("username")));

        if (list.size()==1) {
            return true;
        }else{
            return false;
        }
    }
}
