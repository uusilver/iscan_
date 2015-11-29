package com.tmind.iscan.controller;

import com.tmind.iscan.entity.M_USER_ACCOUNT;
import com.tmind.iscan.entity.M_USER_ACCOUNT_OPT;
import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.service.UserAccountService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lijunying on 15/11/29.
 */
@Controller
@RequestMapping("/zhanghao")
public class UserAccountController {

    @Resource(name="userAccountService")
    private UserAccountService userAccountService;

    @RequestMapping(value="/queryUserAccount", method = RequestMethod.GET)
    @ResponseBody
    public String queryUserAccount(HttpServletRequest req){
        M_USER_ACCOUNT model = userAccountService.queryAccountForDisplay(LoginController.getLoginUser(req).getUserId());
        return model.getAccount()+"-"+model.getCurrency();
    }

    @RequestMapping(value="/purChaseQrAccount/{no4Purchase}", method = RequestMethod.GET)
    @ResponseBody
    public String purChaseQrAccount(@PathVariable String no4Purchase, HttpServletRequest req){
        Integer userId = LoginController.getLoginUser(req).getUserId();
        //再一次检查
        M_USER_ACCOUNT model = userAccountService.queryAccountForDisplay(LoginController.getLoginUser(req).getUserId());
        Integer purchaseAmount = Integer.valueOf(no4Purchase);
        //购买的二维码数量所需的金额
        BigDecimal tempBig = BigDecimal.valueOf(purchaseAmount);
        //实际需要金额
        BigDecimal realBig = tempBig.multiply(BigDecimal.valueOf(0.1));
        //账户余额要大于，可购买的
        if(model.getCurrency().compareTo(realBig)==1||model.getCurrency().compareTo(realBig)==0){
            //开始购买操作，先减款
            model.setCurrency(model.getCurrency().subtract(realBig));
            model.setAccount(model.getAccount()+purchaseAmount);
            //更新用户购买纪录
            M_USER_ACCOUNT_OPT opt = new M_USER_ACCOUNT_OPT();
            opt.setAccount_purchase(purchaseAmount);
            opt.setCurrent_left(model.getCurrency().subtract(realBig));
            opt.setUser_id(userId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            opt.setUpdate_time(sdf.format(new Date()));
            opt.setReason("充值");
            userAccountService.purchaseQrAmountAndKeepRecordIntoOptTable(model,opt);
            return "success";
        }else{
            return "failed";
        }

    }

    @RequestMapping(value="/queryUserDetail", method = RequestMethod.POST)
    @ResponseBody
    public  String queryUserDetail(@RequestParam String aoData, HttpServletRequest req){
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
        List<M_USER_ACCOUNT_OPT> list = userAccountService.queryUserOpt(LoginController.getLoginUser(req).getUserId());
        for (int i = 0; i < list.size(); i++) {

            String[] d = {
                    strLize(list.get(i).getId()),
                    strLize(list.get(i).getUpdate_time()),
                    strLize(list.get(i).getAccount_purchase()),
                    strLize(list.get(i).getAccount_consume()),
                    strLize(list.get(i).getCurrent_left()),
                    strLize(list.get(i).getReason())

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
