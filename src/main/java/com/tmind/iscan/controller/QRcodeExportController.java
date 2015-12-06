package com.tmind.iscan.controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //需要区别新生成的二维码和已经导出的二维码，通过数据库中的Flag来控制
    @RequestMapping(value="download_qrcode_export/{productId}/{productBath}", method = RequestMethod.GET)
    public String download_qrcode_export(@PathVariable("productId") String productId,@PathVariable("productBath") String productBath , HttpServletRequest request,HttpServletResponse response) throws IOException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName="二维码数据导出"+sdf.format(new Date());
        //填充projects数据
        List<QrCodeExportModel> qrcodeModel=createData(LoginController.getLoginUser(request).getUserId(),productId, productBath);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(qrcodeModel).write(os);
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

    //重新导出
    @RequestMapping(value="download_qrcode_re_export/{productId}/{productBath}", method = RequestMethod.GET)
    public String download_qrcode_re_export(@PathVariable("productId") String productId,@PathVariable("productBath") String productBath , HttpServletRequest request,HttpServletResponse response) throws IOException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName="二维码数据导出"+sdf.format(new Date());
        //填充projects数据
        List<QrCodeExportModel> qrcodeModel=createReExportData(LoginController.getLoginUser(request).getUserId(),productId, productBath);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(qrcodeModel).write(os);
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
        //创建第一行
        list4PrintModel.add(new QrCodeExportModel("二维码地址","唯一识别码"));
        //真正的数据
        for(M_USER_QRCODE_ENTITY entityModel:list){
            list4PrintModel.add(new QrCodeExportModel(entityModel.getQr_query_string(),entityModel.getQr_query_string().split("=")[2]));
        }

        return list4PrintModel;
    }

    private List<QrCodeExportModel> createReExportData(Integer userId, String productId, String batchId) {
        //查询具体的二维码
        List<M_USER_QRCODE_ENTITY> list = qrCodeService.queryQrCodeForReExport(userId,productId,batchId);
        List<QrCodeExportModel> list4PrintModel = new ArrayList<QrCodeExportModel>();
        //创建第一行
        list4PrintModel.add(new QrCodeExportModel("二维码地址","唯一识别码"));
        //真正的数据
        for(M_USER_QRCODE_ENTITY entityModel:list){
            list4PrintModel.add(new QrCodeExportModel(entityModel.getQr_query_string(),entityModel.getQr_query_string().split("=")[2]));
        }

        return list4PrintModel;
    }

    private List<Map<String, Object>> createExcelRecord(List<QrCodeExportModel> projects) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
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
