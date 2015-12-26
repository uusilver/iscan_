package com.tmind.iscan.controller;

import com.tmind.iscan.entity.M_USER_ADVICE_TEMPLATE;
import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.model.BatchQueryTo;
import com.tmind.iscan.service.BatchService;
import com.tmind.iscan.service.ProductService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunying on 15/11/28.
 */
@Controller
@RequestMapping("chanpin")
public class BatchController {


    @Resource(name = "batchService")
    private BatchService batchService;

    @RequestMapping(value="/queryBatchData",method = RequestMethod.POST)
    @ResponseBody
    public  String queryBatchData(@RequestParam String aoData, @RequestParam String searchType, @RequestParam String searchContent, HttpServletRequest req){
        System.out.println(searchType);
        System.out.println(searchContent);

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


        List<String[]> lst = new ArrayList<String[]>();
        Integer userId = LoginController.getLoginUser(req).getUserId();
        List<BatchQueryTo> batchModel = batchService.queryProductInfo(userId,searchType,searchContent,iDisplayStart,iDisplayLength);
        for (int i = 0; i < batchModel.size(); i++) {
            List<M_USER_ADVICE_TEMPLATE> adviceTemplates = batchService.queryBatch(userId);
            //必须绑定了相关批次，才能在批次功能里面看到
//            if(productList.get(i).getRelate_batch().length()>0){
                String[] d = {
                        strLize(batchModel.get(i).getId()),
                        strLize(batchModel.get(i).getProductId()),
                        strLize(batchModel.get(i).getProductName()),
                        generateOptions(batchModel.get(i).getAdviceTemplate(), adviceTemplates),
                        strLize(batchModel.get(i).getBatchNo()),
                        strLize(batchModel.get(i).getQrTotalNo()),
                        strLize(batchModel.get(i).getUpdateTime()),
                        strLize(getBatchParams(batchModel.get(i).getParams(),batchModel.get(i).getSellArthor())),
                        "<button class=\"export\">导出旧品牌码</button><button class=\"qrcode\">创建新品牌码</button><button class=\"delete\">删除</button>"
                };

                lst.add(d);
//            }

        }

        JSONObject getObj = new JSONObject();
        getObj.put("sEcho", sEcho);// 不知道这个值有什么用,有知道的请告知一下
        getObj.put("iTotalRecords", batchService.getProductBatchTotalNo(userId,searchType, searchContent));//实际的行数
        getObj.put("iTotalDisplayRecords", lst.size());//显示的行数,这个要和上面写的一样
        getObj.put("aaData", lst);//要以JSON格式返回
        return getObj.toString();
    }


    @RequestMapping(value = "/deleteQrcode/{productId}/{batchNo}", method = RequestMethod.GET)
    public @ResponseBody String deleteQrcode(@PathVariable String productId,@PathVariable String batchNo, HttpServletRequest req){
        if(batchService.deleteQrCodes(LoginController.getLoginUser(req).getUserId(),productId, batchNo)){
            return "success";
        }else{
            return "failed";
        }

    }

    private String strLize(Object obj){
        return String.valueOf(obj);
    }

    private  String generateOptions(String selectValue, List<M_USER_ADVICE_TEMPLATE> templates){
        StringBuilder sb = new StringBuilder();
        sb.append("<select class='tempClass'>");
        for(M_USER_ADVICE_TEMPLATE temp : templates){
            if(temp.getTemplate_name().equalsIgnoreCase(selectValue)){
                sb.append(" <option selected=\"selected\" value="+temp.getTemplate_name()+">"+temp.getTemplate_label()+"</option>");
            }else{
                sb.append(" <option value="+temp.getTemplate_name()+">"+temp.getTemplate_label()+"</option>");
            }
        }
        sb.append("</select>");
        return sb.toString();
    }

    private String getBatchParams(String params, String sellArthor){
        String paramsPool[] = params.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : paramsPool) {
            //添加更多标签的时候这里需要修改
            //TODO 改成从数据库动态查询
            if (s.equals("ud")) {
                sb.append("参数:产品生产时间,<br/>");
            }
            if (s.equals("sl")) {
                sb.append("参数:产品指定经销商:" + sellArthor + ",<br/>");
            }
            if (s.equals("lqd")) {
                sb.append("参数:上次查询时间"+",<br/>");

            }
        }
            String orgStr = sb.toString();
            String newStr = orgStr.substring(0, orgStr.length()-6);
            return  newStr;
    }
}
