package com.example.serverdemo.web;

import com.example.serverdemo.po.User;
import com.example.serverdemo.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping(value = "/login")
    @ResponseBody
    public String insertUser(String name,String pass){
        String response;
        if("lujw".equals(name)&&"123".equals(pass)){
            System.out.println("登陆成功");
            User user=new User();
            user.setUserName(name);
            user.setPassword(pass);
            response=testService.insertUser(user);
        }else{
            response="登陆失败";
        }
        return response;
    }

    @GetMapping(value = "/cookice")
    @ResponseBody
    public String insertCookie(String name,String pass){
        String response;
        if("lujw".equals(name)&&"123".equals(pass)){
            System.out.println("登陆成功");
            User user=new User();
            user.setUserName(name);
            user.setPassword(pass);
            response=testService.insertCookie(user);
        }else{
            response="登陆失败";
        }
        return response;
    }

}
