package com.marshio.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author masuo
 * @data 9/2/2022 下午3:04
 * @Description 控制器
 */

@Controller
@RequestMapping("user")
public class UserController {

    /**
     * 必须携带exists参数，值无所谓
     *
     * @return 地址
     */
    @RequestMapping(value = "login", params = "exists")
    public String login() {
        return "login";
    }

    /**
     * 必须携带status参数，且参数值必须为1
     *
     * @return 地址
     */
    @RequestMapping(value = "logout", params = "status=1")
    public String logout() {
        return "logout";
    }

    @RequestMapping(value = "testPut", method = RequestMethod.PUT)
    public String testPut() {
        return "success";
    }

    @RequestMapping(value = "testParams1", params = {"username", "password"})
    public String testParams1() {
        return "success";
    }

    /**
     * params带叹号表明不能有此参数
     *
     * @return 地址
     */
    @RequestMapping(value = "testParams2", params = {"username", "!password"})
    public String testParams2() {
        return "success";
    }

    @RequestMapping(value = "testParams3", params = {"username", "password!=123"})
    public String testParams3() {
        return "success";
    }

    /**
     * 测试Request Mapping的路径的Restful风格，
     * 原始请求路径：testRestful?id=1&name=ms
     * restful请求路径：testRestful/1/ms,此时需要相应的更改RequestMapping的value值的方式，如下
     * restful即占位符，只能通过@PathVariable注解获取
     * @return 地址
     */
    @RequestMapping(value = "testRestful/{id}/{name}")
    public String testRestful(@PathVariable("id") Integer id, @PathVariable("name") String name) {
        System.out.println(id + name);
        return "success";
    }


}
