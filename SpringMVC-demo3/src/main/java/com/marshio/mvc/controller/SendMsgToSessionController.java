package com.marshio.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author masuo
 * @data 11/2/2022 上午8:41
 * @Description 向session域对象发送数据，session域对象的范围在此次对话都有效
 *              其范围大于request域，小于application域
 */

@Controller
@RequestMapping("sendMsgToSession")
public class SendMsgToSessionController {


    // @RequestMapping({"/",""})
    // public String sendMsg(){
    //     return "sendMsg";
    // }

    @RequestMapping("session")
    public String sendMsgBySession(String username,String password, HttpSession session){

        session.setAttribute("msg","session msg");
        session.setAttribute("username",username);
        session.setAttribute("password",password);
        return "success";
    }
}
