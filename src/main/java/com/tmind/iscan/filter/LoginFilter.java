package com.tmind.iscan.filter;

/**
 * Created by lijunying on 15/11/14.
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.tmind.iscan.controller.LoginController;
import com.tmind.iscan.model.UserTo;

public class LoginFilter implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Language", "zh-CN");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        String path = request.getRequestURI();
        if (path.indexOf("/login") > -1 || path.endsWith(".css")
                || path.endsWith(".js") || path.endsWith(".woff")
                || path.endsWith(".png") || path.endsWith(".jpg") || path.indexOf("rest") > -1) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        UserTo user = LoginController.getLoginUser(request);
        if (user == null) {
            response.sendRedirect("login.html");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
