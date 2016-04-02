package com.tmind.iscan.controller;

import com.tmind.iscan.entity.M_USER_PRODUCT_META;
import com.tmind.iscan.entity.UserEntity;
import com.tmind.iscan.service.AgencyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunying on 16/1/14.
 */
@Controller
@RequestMapping("agency")
public class AgencyController {

    Log log = LogFactory.getLog(AgencyController.class);

    @Resource(name = "agencyService")
    private AgencyService agencyService;


    @RequestMapping(value = "/queryAgencyUserData", method = RequestMethod.POST)
    public @ResponseBody String queryAgencyUserData(@RequestParam String aoData, HttpServletRequest req){
        JSONArray jsonarray = JSONArray.fromObject(aoData);

        String sEcho = null;
        int iDisplayStart = 0; // 起始索引
        int iDisplayLength = 0; // 每页显示的行数

        for (int i = 0; i < jsonarray.size(); i++) {
            JSONObject obj = (JSONObject) jsonarray.get(i);
            if (obj.get("name").equals("sEcho"))
                sEcho = obj.get("value").toString();

            if (obj.get("name").equals("iDisplayStart"))
                iDisplayStart = obj.getInt("value");

            if (obj.get("name").equals("iDisplayLength"))
                iDisplayLength = obj.getInt("value");
        }

        // 生成20条测试数据
        List<String[]> lst = new ArrayList<String[]>();
        List<UserEntity> productList = agencyService.queryUserInfo(LoginController.getLoginUser(req).getUserId(), iDisplayStart, iDisplayLength);
        for (int i = 0; i < productList.size(); i++) {
            String relatedBatch = null;
            /*
            <th>服务状态</th>
            <th>操作</th>

             */
            String active = null;
            if(productList.get(i).getActive_flag()==1){
                 active = "运行";
            }else{
                 active = "停止";

            }
            String[] d = {
                    strLize(i+1),
                    strLize(productList.get(i).getId()),
                    strLize(productList.get(i).getUser_factory_name()),
                    strLize(agencyService.getProductMetaTotalNo(productList.get(i).getId())),
                    strLize(agencyService.getUserAccountNo(productList.get(i).getId())),
                    strLize(productList.get(i).getCreate_date()),
                    strLize(productList.get(i).getExpire_date()),
                    strLize(active),
                    "<button class=\"stop\">停止</button> <button class=\"start\">启动</button>"
            };

            lst.add(d);
        }

        JSONObject getObj = new JSONObject();
        getObj.put("sEcho", sEcho);// 不知道这个值有什么用,有知道的请告知一下
        getObj.put("iTotalRecords", lst.size());//实际的行数
        getObj.put("iTotalDisplayRecords", productList.size());//显示的行数,这个要和上面写的一样
        getObj.put("aaData", lst);//要以JSON格式返回
        return getObj.toString();
    }

    @RequestMapping(value = "/stopUser/{userId}", method = RequestMethod.GET)
    public @ResponseBody String stopUser(@PathVariable("userId") String userId, HttpServletRequest req){
        UserEntity user = agencyService.getUserById(Integer.valueOf(userId));
        user.setActive_flag(0);
        agencyService.updateUser(user);
        return "success";
    }

    @RequestMapping(value = "/startUser/{userId}", method = RequestMethod.GET)
    public @ResponseBody String startUser(@PathVariable("userId") String userId, HttpServletRequest req){
        UserEntity user = agencyService.getUserById(Integer.valueOf(userId));
        user.setActive_flag(1);
        agencyService.updateUser(user);
        return "success";
    }

    //private methods
    private String strLize(Object obj){
        return String.valueOf(obj);
    }
}
