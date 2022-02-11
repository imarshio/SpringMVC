package com.marshio.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author masuo
 * @data 10/2/2022 下午5:22
 * @Description 向request域对象发送数据，request域在一次请求的生命周期内有效，
 */

@Controller
@RequestMapping("sendMsgToReq")
public class SendMsgToRequestController {

    // @RequestMapping({"/",""})
    // public String sendMsg(){
    //     return "sendMsg";
    // }

    /**
     * 使用ServletAPI获取
     * @return success
     */
    @RequestMapping("servletApi")
    public String sendMsgByServletApi(HttpServletRequest request){

        //前端页面获取Request域对象的属性值时，只需要使用${attributeName}就可获取
        request.setAttribute("msg","servlet msg");
        request.setAttribute("username",request.getParameter("username"));
        request.setAttribute("password",request.getParameter("password"));
        return "success";
    }

    /**
     * 使用ModelAndView获取
     * @return success
     */
    @RequestMapping("modelAndView")
    public ModelAndView sendMsgByModelAndView(String username,String password){
        ModelAndView mav = new ModelAndView();
        //前端页面获取Request域对象的属性值时，只需要使用${attributeName}就可获取
        mav.addObject("msg","modelAndView msg");
        mav.addObject("username",username);
        mav.addObject("password",password);
        mav.setViewName("success");
        return mav;
    }

    /**
     * 使用map获取
     * @return success
     */
    @RequestMapping("map")
    public String sendMsgByMap(String username, String password, Map<String, Object> map){
        map.put("msg","map msg");
        map.put("username",username);
        map.put("password",password);
        return "success";
    }

    /**
     * 使用model获取
     * @return success
     */
    @RequestMapping("model")
    public String sendMsgByModel(String username,String password,Model model){
        model.addAttribute("msg","model msg");
        model.addAttribute("username",username);
        model.addAttribute("password",password);
        return "success";
    }

    /**
     * 使用modelMap获取
     * @return success
     */
    @RequestMapping("modelMap")
    public String sendMsgByModelMap(String username, String password, ModelMap modelMap){
        modelMap.addAttribute("msg","modelMap msg");
        modelMap.addAttribute("username",username);
        modelMap.addAttribute("password",password);
        return "success";
    }

}
