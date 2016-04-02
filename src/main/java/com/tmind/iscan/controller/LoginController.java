package com.tmind.iscan.controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.tmind.iscan.entity.AgentUser;
import com.tmind.iscan.entity.UserEntity;
import com.tmind.iscan.model.UserTo;
import com.tmind.iscan.service.UserAccountService;
import com.tmind.iscan.service.UserValidationService;
import com.tmind.iscan.util.HibernateUtil;
import com.tmind.iscan.util.Mail;
import com.tmind.iscan.util.MailUtil;
import com.tmind.iscan.util.UniqueKeyGenerator;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by lijunying on 15/11/13.
 */

@Controller
@RequestMapping("login")
public class LoginController {

    Logger log = Logger.getLogger(LoginController.class);

    //For 验证码

    private int width = 90;//定义图片的width
    private int height = 20;//定义图片的height
    private int codeCount = 4;//定义图片上显示验证码的个数
    private int xx = 15;
    private int fontHeight = 18;
    private int codeY = 16;
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };


    // Standard JSR250 Injection
    @Resource(name = "userValService")
    private UserValidationService userValidation;

    @Resource(name = "userAccountService")
    private UserAccountService userAccountService;

    @RequestMapping(params = "weblogin")
    public ModelAndView login(@ModelAttribute("user") UserTo userTo,
                        HttpServletRequest req, HttpServletResponse response) {
        UserEntity userEntity = userValidation.findUserEntity(userTo.getUsername(), userTo.getPassword());
        Map<String,Object> model = new HashMap<String,Object>();
        if(userEntity!=null) {
            if (userEntity.getActive_flag() == 0) {
                model.put("error", "用户被暂停，请联系您的代理商");
                return new ModelAndView("fail", model);

            } else {
                userTo.setUserId(userEntity.getId());
                userTo.setQuery_qrcode_table(userEntity.getQuery_qrcode_table());
                userTo.setUser_type(userEntity.getUser_type());
                userTo.setUser_email(userEntity.getUser_email());
                userTo.setUser_factory_name(userEntity.getUser_factory_name());
                userTo.setUser_factory_address(userEntity.getUser_factory_address());
                userTo.setUser_contact_person_name(userEntity.getUser_contact_person_name());
                req.getSession().setAttribute("userInSession", userTo);
                return new ModelAndView("index", model);
            }
        }else {
            model.put("error", "用户名或密码错误");
            return new ModelAndView("fail", model);

        }
    }

    @RequestMapping(params = "agencyLogin")
    public String agencyLogin(@ModelAttribute("user") UserTo userTo,
                        HttpServletRequest req, HttpServletResponse response) {
        AgentUser user = userValidation.findAgentUserInDatabase(userTo.getUsername(),userTo.getPassword());
        userTo.setUserId(user.getId());
        req.getSession().setAttribute("userInSession", userTo);
        return "agency/index";
    }

    @RequestMapping(value="getNewPass")
    public @ResponseBody String getNewPass(@RequestParam String username, @RequestParam String email){
        UserEntity userEntity = userValidation.findUserEntityByUsername(username,email);
        String result = null;
        if(userEntity!=null){
            String newPass = UniqueKeyGenerator.generateShortUuid();
            if(MailUtil.sendMail(userEntity.getUser_email(),"密码重置","您好, 新密码是:"+newPass)){
                userEntity.setPassword(newPass);
                if(userAccountService.updateUserInfo(userEntity))
                    result =  "success";
            }
        }else{
            result = "failed";
        }
        return result;
    }

    @RequestMapping("/code")
    public void getCode(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

            // 定义图像buffer
            BufferedImage buffImg = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
//      Graphics2D gd = buffImg.createGraphics();
            //Graphics2D gd = (Graphics2D) buffImg.getGraphics();
            Graphics gd = buffImg.getGraphics();
            // 创建一个随机数生成器类
            Random random = new Random();
            // 将图像填充为白色
            gd.setColor(Color.WHITE);
            gd.fillRect(0, 0, width, height);

            // 创建字体，字体的大小应该根据图片的高度来定。
            Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
            // 设置字体。
            gd.setFont(font);

            // 画边框。
            gd.setColor(Color.BLACK);
            gd.drawRect(0, 0, width - 1, height - 1);

            // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
            gd.setColor(Color.BLACK);
            for (int i = 0; i < 40; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                gd.drawLine(x, y, x + xl, y + yl);
            }

            // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
            StringBuffer randomCode = new StringBuffer();
            int red = 0, green = 0, blue = 0;

            // 随机产生codeCount数字的验证码。
            for (int i = 0; i < codeCount; i++) {
                // 得到随机产生的验证码数字。
                String code = String.valueOf(codeSequence[random.nextInt(36)]);
                // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);

                // 用随机产生的颜色将验证码绘制到图像中。
                gd.setColor(new Color(red, green, blue));
                gd.drawString(code, (i + 1) * xx, codeY);

                // 将产生的四个随机数组合在一起。
                randomCode.append(code);
            }
            // 将四位数字的验证码保存到Session中。
            HttpSession session = req.getSession();
            System.out.print(randomCode);
            session.setAttribute("code", randomCode.toString());

            // 禁止图像缓存。
            resp.setHeader("Pragma", "no-cache");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setDateHeader("Expires", 0);

            resp.setContentType("image/jpeg");

            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos = resp.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
            sos.close();

    }

    @RequestMapping(value = "validateJCode", method = RequestMethod.POST)
    public @ResponseBody String validateJCode(@RequestParam String jCode, HttpServletRequest request){
        String jCodeInSession = (String)request.getSession().getAttribute("code");
        if(jCodeInSession!=null&&jCodeInSession.equals(jCode)){
            return "success";
        }else{
            return "failed";
        }
    }

    // Get Login user session
    public static UserTo getLoginUser(HttpServletRequest req) {
        return (UserTo) req.getSession().getAttribute("userInSession");
    }


}
