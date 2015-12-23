package com.tmind.iscan.controller;

import com.tmind.iscan.entity.M_USER_PRODUCT_META;
import com.tmind.iscan.entity.M_USER_QRCODE_ENTITY;
import com.tmind.iscan.service.ProductService;
import com.tmind.iscan.service.QrCodeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunying on 15/12/23.
 */
@Controller
@RequestMapping("tongji")
public class UserQrcodeController {

    @Resource(name="qrCodeService")
    private QrCodeService qrCodeService;

    @Resource(name="productService")
    private ProductService productService;

    @RequestMapping(value="/queryUserAllQrCodeInfo",method = RequestMethod.GET)
    public @ResponseBody String queryUserAllQrCodeInfo(@RequestParam String aoData,HttpServletRequest req) {

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
        List<M_USER_QRCODE_ENTITY> list = qrCodeService.queryAllQrcodetInfo(LoginController.getLoginUser(req).getUserId(), iDisplayStart, iDisplayLength);
        for (int i = 0; i < list.size(); i++) {
            String relatedBatch = null;
            relatedBatch = "<button class=\"addBatch\">添加批次号</button>";
            M_USER_PRODUCT_META productMeta = productService.queryProductInfoById(LoginController.getLoginUser(req).getUserId(), list.get(i).getProduct_id());
            String[] d = {
                    strLize(list.get(i).getId()),
                    strLize(productMeta.getProduct_name()),
                    strLize(list.get(i).getProduct_batch()),
                    strLize(list.get(i).getQuery_date()),
                    strLize(list.get(i).getVistor_phy_addr()),
                    strLize(list.get(i).getQuery_times()>1?"<font color='red'>"+list.get(i).getQuery_times()+"</font>":list.get(i).getQuery_times())
                    //未来开放
//                    "<button class=\"edit\">编辑</button> <button class=\"qrcode\">查看品牌码库</button>  <button class=\"delete\">删除</button>"
            };

            lst.add(d);
        }
        JSONObject getObj = new JSONObject();
        getObj.put("sEcho", sEcho);// 不知道这个值有什么用,有知道的请告知一下
        getObj.put("iTotalRecords", lst.size());//实际的行数
        getObj.put("iTotalDisplayRecords", qrCodeService.getQrcodeTotalNo(LoginController.getLoginUser(req).getUserId()));//显示的行数,这个要和上面写的一样
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

    //private methods
    private String strLize(Object obj){
        return String.valueOf(obj);
    }

}
