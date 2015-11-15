package com.tmind.iscan.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by lijunying on 15/11/15.
 */
public class SecurityUtil {

    //MD5 加密
    public static String encodeWithMd5Hash(String oriStr){
        MessageDigest md = null;//SHA 或者 MD5
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BASE64Encoder  base = new BASE64Encoder ();
        String hashStr = base.encode(md.digest(oriStr.getBytes()));
        return hashStr;
    }

    //Base64加密
    public static String encodeWithBase64(String oriStr){
        byte[] b = null;
        String s = null;
        try {
            b = oriStr.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    //Base64解密
    public static String decodeStringWithBase64(String encodedStr){
        byte[] b = null;
        String result = null;
        if (encodedStr != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(encodedStr);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    public static void main(String args[]){
        String password = "password";
        System.out.println(SecurityUtil.encodeWithMd5Hash(password));
        String encodeStr = SecurityUtil.encodeWithBase64(password);
        System.out.println(encodeStr);
        System.out.println(SecurityUtil.decodeStringWithBase64(encodeStr));

    }
}
