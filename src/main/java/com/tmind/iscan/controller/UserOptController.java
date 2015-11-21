package com.tmind.iscan.controller;

import com.tmind.iscan.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.iscan.model.UserTo;
import com.tmind.iscan.service.UserOptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        userOptService.createUserProducet(product);
        System.out.println("新产品创建成功");
        return "/chanpin/code";
    }
}
