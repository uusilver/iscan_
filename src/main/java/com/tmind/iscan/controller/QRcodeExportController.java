package com.tmind.iscan.controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmind.iscan.entity.M_USER_QRCODE_ENTITY;
import com.tmind.iscan.model.QrCodeExportModel;
import com.tmind.iscan.service.QrCodeService;
import com.tmind.iscan.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lijunying on 15/11/28.
 */
@Controller
public class QRcodeExportController {

    @Resource(name="qrCodeService")
    private QrCodeService qrCodeService;

    @RequestMapping(value="download_qrcode/{productId}/{productBath}", method = RequestMethod.GET)
    public String download(@PathVariable("productId") String productId,@PathVariable("productBath") String productBath , HttpServletRequest request,HttpServletResponse response) throws IOException{
        String fileName="excel文件";
        //填充projects数据
        List<QrCodeExportModel> qrcodeModel=createData(LoginController.getLoginUser(request).getUserId(),productId, productBath);
        List<Map<String,Object>> list=createExcelRecord(qrcodeModel);
        String columnNames[]={"访问","项目名","销售人","负责人","所用技术","备注"};//列名
        String keys[]    =     {"url"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list, keys).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }
    private List<QrCodeExportModel> createData(Integer userId, String productId, String batchId) {
        //查询具体的二维码
        List<M_USER_QRCODE_ENTITY> list = qrCodeService.queryQrCode(userId,productId,batchId);
        List<QrCodeExportModel> list4PrintModel = new ArrayList<QrCodeExportModel>();
        for(M_USER_QRCODE_ENTITY entityModel:list){
            list4PrintModel.add(new QrCodeExportModel(entityModel.getQr_query_string()));
        }

        return list4PrintModel;
    }
    private List<Map<String, Object>> createExcelRecord(List<QrCodeExportModel> projects) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        QrCodeExportModel project=null;
        for (int j = 0; j < projects.size(); j++) {
            project=projects.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("url", project.getVisitUrl());

            listmap.add(mapValue);
        }
        return listmap;
    }
}
