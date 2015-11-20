package com.tmind.iscan.controller;

import com.tmind.iscan.service.LoadSystemInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lijunying on 15/11/20.
 */
@Controller
@RequestMapping("/mainController")
public class MainController {

    Logger log = Logger.getLogger(MainController.class);

    @Resource(name = "loadSysInfo")
    private LoadSystemInfoService loadSystemInfoService;

    @RequestMapping(value="/getSystemInfo", method = RequestMethod.GET)
    public @ResponseBody String getSystemInfo(){
        String message = loadSystemInfoService.loadSysInfo();
        System.out.println("系统消息:"+message);
        return message;
    }
}
