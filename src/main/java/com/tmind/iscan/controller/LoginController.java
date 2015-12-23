package com.tmind.iscan.controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.tmind.iscan.entity.UserEntity;
import com.tmind.iscan.model.UserTo;
import com.tmind.iscan.service.UserAccountService;
import com.tmind.iscan.service.UserValidationService;
import com.tmind.iscan.util.HibernateUtil;
import com.tmind.iscan.util.Mail;
import com.tmind.iscan.util.MailUtil;
import com.tmind.iscan.util.UniqueKeyGenerator;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by lijunying on 15/11/13.
 */

@Controller
@RequestMapping("login")
public class LoginController {

    Logger log = Logger.getLogger(LoginController.class);

    // Standard JSR250 Injection
    @Resource(name = "userValService")
    private UserValidationService userValidation;

    @Resource(name = "userAccountService")
    private UserAccountService userAccountService;

    @RequestMapping(params = "weblogin")
    public String login(@ModelAttribute("user") UserTo userTo,
                        HttpServletRequest req, HttpServletResponse response) {

        log.info("当前登陆:"+userTo.toString());
        UserEntity userEntity = userValidation.findUserEntity(userTo.getUsername(), userTo.getPassword());
        if (userEntity.getId()>0) {
                userTo.setUserId(userEntity.getId());
                userTo.setQuery_qrcode_table(userEntity.getQuery_qrcode_table());
                userTo.setUser_type(userEntity.getUser_type());
                userTo.setUser_email(userEntity.getUser_email());
                userTo.setUser_factory_name(userEntity.getUser_factory_name());
                userTo.setUser_factory_address(userEntity.getUser_factory_address());
                userTo.setUser_contact_person_name(userEntity.getUser_contact_person_name());
                req.getSession().setAttribute("userInSession", userTo);
                return "index";
        }
        return "login?1";
    }

    @RequestMapping(value="getNewPass")
    public @ResponseBody String getNewPass(@RequestParam String username, @RequestParam String email){
        UserEntity userEntity = userValidation.findUserEntityByUsername(username,email);
        String result = null;
        if(userEntity!=null){
            String newPass = UniqueKeyGenerator.generateShortUuid();
            if(MailUtil.sendMail(userEntity.getUser_email(),"密码重置","您好, 新密码是:"+newPass)){
                userEntity.setPassword(newPass);
                if(userAccountService.updateUserInfo(userEntity))
                    result =  "success";
            }
        }else{
            result = "failed";
        }
        return result;
    }

    // Get Login user session
    public static UserTo getLoginUser(HttpServletRequest req) {
        return (UserTo) req.getSession().getAttribute("userInSession");
    }


}
