package com.marshio.mvc.controller;

import com.marshio.mvc.bean.Employee;
import com.marshio.mvc.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author masuo
 * @data 11/2/2022 下午2:33
 * @Description Restful 风格，即将资源当作路径访问具体看代码
 * 为什么要使用Restful,因为这样使代码风格清晰
 * get     查询
 * post    新增
 * put     修改
 * delete  删除
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

    /**
     * 查询
     * @return employees
     */
    @RequestMapping(value = "/employees" , method = RequestMethod.GET)
    public String employees(HttpServletRequest request){
        System.out.println("Getting all employees...");
        request.setAttribute("employees",employeeDao.getAll());
        return "employees";
    }

    /**
     * 新增
     * @return employees
     */
    @RequestMapping(value = "/addEmployee",method = RequestMethod.POST)
    public String addEmployee(Employee employee,HttpServletRequest request){
        String lastName = request.getParameter("lastName");
        System.out.println(lastName);
        System.out.println("Adding a employee"+employee.toString());
        employeeDao.save(employee);
        return "redirect:/employees";
    }

    /**
     * 根据id获取employee，然后跳转到更新页面，
     * @return employees
     */
    @RequestMapping(value = "/toUpdate/{id}",method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("employee",employee);
        return "updateEmployee";
    }

    /**
     * 更新数据，并回显
     * @return employees
     */
    @RequestMapping(value = "/updateEmployee",method = RequestMethod.PUT)
    public String updateEmployee(Employee employee){
        System.out.println("Updating an employee:"+employee.toString());
        employeeDao.save(employee);
        return "redirect:/employees";
    }

    /**
     * 更新数据，并回显
     * @return employees
     */
    @RequestMapping(value = "/del/{id}",method = RequestMethod.DELETE)
    public String delEmployee(@PathVariable("id")Integer id){
        System.out.println("Deleting an employee:"+id);
        employeeDao.delete(id);
        return "redirect:/employees";
    }
}
