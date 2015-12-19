package com.tmind.iscan.controller;

import com.google.gson.Gson;
import com.tmind.iscan.entity.M_USER_ACCOUNT;
import com.tmind.iscan.model.MainPageModelTo;
import com.tmind.iscan.model.UserVistorMapModel;
import com.tmind.iscan.service.LoadSystemInfoService;
import com.tmind.iscan.service.UserAccountService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lijunying on 15/11/20.
 */
@Controller
@RequestMapping("mainController")
public class MainController {

    Logger log = Logger.getLogger(MainController.class);

    @Resource(name = "loadSysInfo")
    private LoadSystemInfoService loadSystemInfoService;

    @Resource(name = "userAccountService")
    private UserAccountService userAccountService;

    @RequestMapping(value = "/getSystemInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    String getSystemInfo(HttpServletRequest req) {
        String message = loadSystemInfoService.loadSysInfo();
        M_USER_ACCOUNT userAccount = userAccountService.queryAccountForDisplay(LoginController.getLoginUser(req).getUserId());
        System.out.println("系统消息:" + message);
        MainPageModelTo model = new MainPageModelTo();
        model.setMessage(message);
        model.setAccount(userAccount.getAccount());
        model.setQr_total_user(userAccount.getQr_total_user());
        model.setScan_total_user(userAccount.getScan_total_user());
        model.setWarning_qr_code_no(userAccount.getWarning_qr_code_no());
        return new Gson().toJson(model);
    }

    @RequestMapping(value = "/getStatisticInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    String getStatisticInfo(HttpServletRequest req) {
        List<UserVistorMapModel> list = new ArrayList<UserVistorMapModel>();
        UserVistorMapModel m1 = new UserVistorMapModel("江苏", 50, "#4572a7");
        UserVistorMapModel m2 = new UserVistorMapModel("北京", 30, "#aa4643");
        UserVistorMapModel m3 = new UserVistorMapModel("上海", 20, "#89a54e");
        list.add(m1);
        list.add(m2);
        list.add(m3);
        return new Gson().toJson(list);
    }

    @RequestMapping(value = "/getUserName", method = RequestMethod.GET)
    public @ResponseBody String getUserName(HttpServletRequest request){
        return LoginController.getLoginUser(request).getUsername();
    }

    @RequestMapping(value = "/logout")
    public @ResponseBody String logout(HttpServletRequest request){
        log.info(LoginController.getLoginUser(request).getUsername()+"在"+new Date()+"退出系统");
        request.getSession().removeAttribute("userInSession");
        return "success";
    }

}
