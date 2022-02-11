package com.marshio.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author masuo
 * @data 9/2/2022 上午9:40
 * @Description 登陆控制器
 * 想要将这个控制器交给Spring有两种方式
 * 1.注解+扫描
 * 2.配置bean
 */

@Controller
public class LoginController {


    /**
     * 访问 /  ==》 就是访问  /templates/ index .html
     * @return login.html
     */
    // @RequestMapping(value = "/")
    // public String login(){
    //     return "index";
    // }

}
