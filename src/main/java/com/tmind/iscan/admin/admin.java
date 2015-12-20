package com.tmind.iscan.admin;

import com.tmind.iscan.service.pservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lijunying on 15/12/20.
 */
@Controller
@RequestMapping("ptool/admin")
public class admin {

    @Resource(name="pservice")
    private pservice pservice;

    @RequestMapping(value = "/queryPerform",method = RequestMethod.POST)
    public @ResponseBody String queryPerform(@RequestParam String p){
        if(p.equals("@!315ck.Com")){
            long prodMetaTime = pservice.getProductMetaQueryTime();
            long prodBatchTime = pservice.getProductBatchQueryTime();
            long categoryTime = pservice.getCategoryQueryTime();
            long qrcodeTime = pservice.getQrcodeQueryTime();
            return prodMetaTime+","+prodBatchTime+","+categoryTime+","+qrcodeTime;
        }else{
            return "null";
        }

    }
}
