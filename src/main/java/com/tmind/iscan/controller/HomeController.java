package com.tmind.iscan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/")
public class HomeController {
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		model.addAttribute("message", "Hello world!");
		return "login";
	}
}