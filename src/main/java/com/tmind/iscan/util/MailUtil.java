package com.tmind.iscan.util;

/**
 * Created by lijunying on 15/12/24.
 */
public class MailUtil {

    public static boolean sendMail(String to, String subject, String content){
        String smtp = "smtp.163.com";
        String from = "13851483034@163.com";
        String username4Mail="13851483034@163.com";
        String password4Mail="19850924";
        if(Mail.send(smtp, from, to, subject, content, username4Mail, password4Mail)){
            System.out.println("邮件发送成功");

            return  true;
        }else{
            return false;
        }
    }
}
