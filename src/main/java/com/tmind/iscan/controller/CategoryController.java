package com.tmind.iscan.controller;

import com.google.gson.Gson;
import com.tmind.iscan.entity.M_USER_CATEGORY_ENTITY;
import com.tmind.iscan.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lijunying on 15/11/22.
 */
@Controller
@RequestMapping("/categoryController")
public class CategoryController {

    @Resource(name="categoryService")
    private CategoryService categoryService;

    @RequestMapping(value="/createCategory", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("M_USER_CATEGORY_ENTITY")M_USER_CATEGORY_ENTITY category, HttpServletRequest req){
        category.setUser_id(LoginController.getLoginUser(req).getUserId());
        categoryService.createCategory(category);
        return "/chanpin/chanpin2";
    }

    @RequestMapping(value = "/queryCategory", method = RequestMethod.GET)
    public @ResponseBody String queryCategory(HttpServletRequest req){
        List<M_USER_CATEGORY_ENTITY> list  = categoryService.queryCategory(LoginController.getLoginUser(req).getUserId());
        return new Gson().toJson(list);
    }

    @RequestMapping(value = "/deleteCategory/{categoryIds}",method = RequestMethod.GET)
    public @ResponseBody String deleteCategory(@PathVariable String categoryIds, HttpServletRequest req){
        String[] deleteIds = categoryIds.split(",");
        Integer userId = LoginController.getLoginUser(req).getUserId();
        boolean flag = false;
        for(String id:deleteIds){
            if(categoryService.deleteCategory(Integer.parseInt(id),userId)){
                flag = true;
            }
        }
        if(flag){
            return "success";
        }else{
            return "failed";
        }

    }
}
