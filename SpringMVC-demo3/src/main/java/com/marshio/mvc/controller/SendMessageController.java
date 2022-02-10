package com.marshio.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author masuo
 * @data 10/2/2022 下午5:22
 * @Description 使用域对象共享数据
 */

@Controller
@RequestMapping("sendMsg")
public class SendMessageController {

    @RequestMapping({"/",""})
    public String sendMsg(){
        return "sendMsg";
    }

    /**
     * 使用ServletAPI获取
     * @return success
     */
    @RequestMapping("servletApi")
    public String servletApi(HttpServletRequest request){

        //前端页面获取Request域对象的属性值时，只需要使用${attributeName}就可获取
        request.setAttribute("msg","servlet msg");
        request.setAttribute("username",request.getParameter("username"));
        request.setAttribute("password",request.getParameter("password"));
        return "success";
    }
}
