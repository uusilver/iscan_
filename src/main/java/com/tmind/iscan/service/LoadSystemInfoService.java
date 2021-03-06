package com.tmind.iscan.service;

import com.tmind.iscan.entity.System_meta_Info;
import com.tmind.iscan.entity.UserEntity;
import com.tmind.iscan.util.HibernateUtil;
import com.tmind.iscan.util.SecurityUtil;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/20.
 */
@SuppressWarnings("ALL")
@Service("loadSysInfo")
public class LoadSystemInfoService {

    Logger log = Logger.getLogger(LoadSystemInfoService.class);

    public String loadSysInfo(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery("from System_meta_Info");
        List<System_meta_Info> list = q.list();
        log.info("读取系统信息成功");
        session.close();
        return list.get(0).getSystem_message();
    }
}
