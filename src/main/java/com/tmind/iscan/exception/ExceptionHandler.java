package com.tmind.iscan.exception;


import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lijunying on 15/11/14.
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    Logger log = Logger.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error(e.getMessage());
        System.out.println(e.getMessage());
        return new ModelAndView("error-404");
    }
}
