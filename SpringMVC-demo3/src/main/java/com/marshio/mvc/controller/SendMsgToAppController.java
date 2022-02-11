package com.marshio.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author masuo
 * @data 11/2/2022 上午8:41
 * @Description 向application域对象发送数据。application域是最大的域对象
 */
@Controller
@RequestMapping("sendMsgToApp")
public class SendMsgToAppController {

    @RequestMapping("app")
    public String sendMsgByApp(String username,String password, HttpSession session){

        // 想要通过Application域对象发送数据，就需要先通过session获取application域
        // 在这里application域对象其实就是项目的上下文域对象，即全局对象
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("msg","app msg");
        servletContext.setAttribute("username",username);
        servletContext.setAttribute("password",password);
        return "success";
    }
}
