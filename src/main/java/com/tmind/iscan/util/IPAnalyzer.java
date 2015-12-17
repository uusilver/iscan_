package com.tmind.iscan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lijunying on 15/12/17.
 */
public class IPAnalyzer {

    final static int BUFFER_SIZE = 4096;

    public static String queryAddressByIp(String ip) throws Exception {
        //http://freeapi.ipip.net/58.213.20.42
        //需要请求的restful地址
        URL url = new URL("http://freeapi.ipip.net/" + ip);

        //打开restful链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 提交模式
        conn.setRequestMethod("GET");//POST GET PUT DELETE
        conn.setConnectTimeout(10000);//连接超时 单位毫秒
        conn.setReadTimeout(2000);//读取超时 单位毫秒
        //读取请求返回值
        InputStream inStream = conn.getInputStream();
        String content = InputStreamTOString(inStream);
        String[] list = content.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
        for (String s : list) {
            System.out.println(s);
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            queryAddressByIp("58.213.20.42");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String InputStreamTOString(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }
}
