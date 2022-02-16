package com.marshio.mvc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author masuo
 * @data 15/2/2022 下午5:52
 * @Description 异常处理器
 * ‘@ControllerAdvice’ 将此类标识为异常处理类
 */

@ControllerAdvice
public class ErrorController {

    /**
     * '@ExceptionHandler' 用于设置所标识方法处理的异常
     * @return error
     */
    @ExceptionHandler(NullPointerException.class)
    public String errorHandler(Exception ex, Model model){
        model.addAttribute("ex",ex);
        return "error";
    }
}
