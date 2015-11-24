package com.tmind.iscan.controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.tmind.iscan.entity.UserEntity;
import com.tmind.iscan.model.UserTo;
import com.tmind.iscan.service.UserValidationService;
import com.tmind.iscan.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by lijunying on 15/11/13.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    Logger log = Logger.getLogger(LoginController.class);

    // Standard JSR250 Injection
    @Resource(name = "userValService")
    private UserValidationService userValidation;

    @RequestMapping(params = "weblogin")
    public String login(@ModelAttribute("user") UserTo userTo,
                        HttpServletRequest req, HttpServletResponse response) {

        log.info("当前登陆:"+userTo.toString());
        Integer userId = userValidation.findUserInDatabase(userTo.getUsername(), userTo.getPassword());
        String userQrcodeTable = userValidation.findUserQrTableFromDatabase(userTo.getUsername(), userTo.getPassword());
        if (userId>0) {
                userTo.setUserId(userId);
                userTo.setQuery_qrcode_table(userQrcodeTable);
                req.getSession().setAttribute("userInSession", userTo);
                return "index";
        }
        return "login";
    }

    // Get Login user session
    public static UserTo getLoginUser(HttpServletRequest req) {
        return (UserTo) req.getSession().getAttribute("userInSession");
    }
}
