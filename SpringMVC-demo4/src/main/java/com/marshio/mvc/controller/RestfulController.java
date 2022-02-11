package com.marshio.mvc.controller;

import com.marshio.mvc.dao.EmployeeDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author masuo
 * @data 11/2/2022 下午2:33
 * @Description Restful 风格，即将资源当作路径访问具体看代码
 * 为什么要使用Restful,因为这样使代码风格清晰
 */

@Controller
public class RestfulController {

    EmployeeDao employeeDao;

    /**
     * 构造函数，在构造时在为EmployeeDao赋值
     */
    public RestfulController(){
        employeeDao = new EmployeeDao();
    }

    @RequestMapping(value = "/employees" , method = RequestMethod.GET)
    public String employees(HttpServletRequest request){
        System.out.println("employees");
        request.setAttribute("employees",employeeDao.getAll());
        return "employees";
    }
}
