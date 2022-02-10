package com.marshio.mvc.controller;

import com.marshio.mvc.beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author masuo
 * @data 9/2/2022 下午5:44
 * @Description 测试获取参数方法
 */

@Controller
public class ParamsController {

    @RequestMapping("testGetParams")
    public String getParams() {
        return "params";
    }

    /**
     * 获取参数方法1--ServletAPI
     * 由于SpringMVC中的所有请求都会经过DispatcherServlet，
     * 而经过DispatcherServlet处理的请求都会被封装起来，而被封装的数据里面就包含了传递的参数
     * 我们只需要找到他们就可以了
     *
     * @param request DispatcherServlet会给此形参自动赋值
     * @return params
     */
    @RequestMapping(value = "testGetParamMethod1")
    public String testHttpServletRequest(HttpServletRequest request) {
        //这个在检测到参数为HttpServletRequest，则表示这是一个当前请求
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username:" + username + ",password:" + password);
        return "success";
    }

    /**
     * 用形参获取参数
     * @return success
     */
    @RequestMapping(value = "testGetParamMethod2")
    public String testAutoReject(String username, String password, String[] sex) {
        //使用形参获取参数时，DispatcherServlet会自动注入参数值
        System.out.println("username:" + username + ",password:" + password + ",sex:" + Arrays.toString(sex));
        return "success";
    }

    /**
     * 使用RequestParam获取参数,等同于PathVariable
     * RequestParam注解是参数注解，只能放在参数上
     *
     * @return s
     */
    @RequestMapping(value = "testGetParamMethod3")
    public String testRequestParam(@RequestParam(value = "username", required = false) String username,
                                      @RequestParam(value = "password", defaultValue = "hha") String password,
                                      @RequestParam(value = "sex", required = false, defaultValue = "hha") String sex,
                                      @RequestHeader(value = "host") String host) {
        System.out.println("username:" + username + ",password:" + password + ",sex:" + sex + ",host:" + host);
        return "success";
    }

    /**
     * 使用RequestHeader获取参数
     * @return success
     */
    @RequestMapping(value = "testGetParamMethod4")
    public String testRequestHeader(@RequestHeader(value = "host") String host,
                                      @RequestHeader(value = "User-Agent") String userAgent,
                                      @RequestHeader(value = "Accept") String accept,
                                      @RequestHeader(value = "Accept-Encoding") String acceptEncoding,
                                      @RequestHeader(value = "Connection") String connection) {
        System.out.println("host:" + host);
        System.out.println("User-Agent:" + userAgent);
        System.out.println("Accept" + accept);
        return "success";
    }

    /**
     * 使用RequestHeader获取参数
     * @return success
     */
    @RequestMapping(value = "testGetParamMethod5")
    public String testCookieValue(@CookieValue(value = "newcookie",required = false) Cookie cookie) {
        //System.out.println("host:" + cookie.getValue());
        return "success";
    }

    /**
     * 使用RequestHeader获取参数
     * @return success
     */
    @RequestMapping(value = "testGetParamMethod6")
    public String testPojo(User user) {
        System.out.println("user:" + user.toString());
        return "success";
    }
}
