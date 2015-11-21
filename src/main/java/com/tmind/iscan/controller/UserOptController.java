package com.tmind.iscan.controller;

import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.model.UserTo;
import com.tmind.iscan.service.UserOptService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by lijunying on 15/11/21.
 */
@Controller
@RequestMapping("/userOpt")
public class UserOptController {

    @Resource(name = "userOptService")
    private UserOptService userOptService;

    @RequestMapping("/createProduct")
    public String login(@ModelAttribute("M_USER_PRODUCT_ENTITY") M_USER_PRODUCT_ENTITY product,
                        HttpServletRequest req, HttpServletResponse response){
        UserTo user = LoginController.getLoginUser(req);
        product.setUser_id(user.getUserId());
        product.setProduct_id(strLize(UUID.randomUUID()));
        product.setQrcode_total_no(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        product.setUpdate_time(sdf.format(new Date()));
        userOptService.createUserProducet(product);
        System.out.println("新产品创建成功");
        return "/chanpin/code";
    }

    @RequestMapping(value="/queryProductData",method = RequestMethod.POST)
    @ResponseBody
    public  String queryProductData(@RequestParam String aoData, HttpServletRequest req){
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
        List<M_USER_PRODUCT_ENTITY> productList = userOptService.queryProductInfo(LoginController.getLoginUser(req).getUserId());
        for (int i = 0; i < productList.size(); i++) {
            String[] d = {
                           strLize(productList.get(i).getId()),
                           strLize(productList.get(i).getProduct_id()),
                           strLize(productList.get(i).getProduct_name()),
                           strLize(productList.get(i).getProduct_category()),
                           strLize(productList.get(i).getRelate_batch()),
                           strLize(productList.get(i).getQrcode_total_no()),
                           strLize(productList.get(i).getUpdate_time()),
                           "<button class=\"edit\">编辑</a> <button class=\"qrcode\">生成品牌码</a>  <button class=\"delete\">删除</a>"
                         };

            lst.add(d);
        }

        JSONObject getObj = new JSONObject();
        getObj.put("sEcho", sEcho);// 不知道这个值有什么用,有知道的请告知一下
        getObj.put("iTotalRecords", lst.size());//实际的行数
        getObj.put("iTotalDisplayRecords", lst.size());//显示的行数,这个要和上面写的一样
        try{
            if(!(lst.size()<iDisplayLength)){
                lst.subList(iDisplayStart,iDisplayStart+iDisplayLength);
            }
            getObj.put("aaData", lst);//要以JSON格式返回

        }catch (Exception e){
            e.printStackTrace();
        }
        return getObj.toString();
    }

    private String strLize(Object obj){
        return String.valueOf(obj);
    }
}
