package com.tmind.iscan.controller;

import com.tmind.iscan.entity.M_USER_ACCOUNT;
import com.tmind.iscan.service.LoadSystemInfoService;
import com.tmind.iscan.service.UserAccountService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lijunying on 15/11/20.
 */
@Controller
@RequestMapping("mainController")
public class MainController {

    Logger log = Logger.getLogger(MainController.class);

    @Resource(name = "loadSysInfo")
    private LoadSystemInfoService loadSystemInfoService;

    @Resource(name="userAccountService")
    private UserAccountService userAccountService;

    @RequestMapping(value="/getSystemInfo", method = RequestMethod.GET)
    public @ResponseBody String getSystemInfo(HttpServletRequest req){
        String message = loadSystemInfoService.loadSysInfo();
        M_USER_ACCOUNT userAccount = userAccountService.queryAccountForDisplay(LoginController.getLoginUser(req).getUserId());
        System.out.println("系统消息:"+message);
        return message+","+userAccount.getAccount();
    }
}
