package com.marshio.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author masuo
 * @data 9/2/2022 下午5:44
 * @Description 测试获取参数方法
 */

@Controller
public class ParamsController {

    @RequestMapping("testGetParams")
    public String getParams(){
        return "params";
    }

    /**
     * 获取参数方法1--ServletAPI
     * 由于SpringMVC中的所有请求都会经过DispatcherServlet，
     * 而经过DispatcherServlet处理的请求都会被封装起来，而被封装的数据里面就包含了传递的参数
     * 我们只需要找到他们就可以了
     * @param request DispatcherServlet会给此形参自动赋值
     * @return params
     */
    @RequestMapping(value = "testGetParamMethod1")
    public String testGetParamMethod1(HttpServletRequest request) {
        //这个在检测到参数为HttpServletRequest，则表示这是一个当前请求
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        return "params";
    }
}
