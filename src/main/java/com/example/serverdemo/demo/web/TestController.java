package com.example.serverdemo.demo.web;

import com.example.serverdemo.demo.po.User;
import com.example.serverdemo.demo.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/dev")
@RestController
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping(value = "/check")
    @ResponseBody
    public String insertUser(String userName,String password,String authCode){
        String response;
        if("lujw".equals(userName)&&"123".equals(password)){
            System.out.println("登陆成功");
            User user=new User();
            user.setUserName(userName);
            user.setPassword(password);
            testService.insertUser(user);
            response="success";
        }else{
            response="登陆失败";
        }
        return response;
    }

    @GetMapping(value = "/authCode")
    @ResponseBody
    public void insertCookie( ){

    }

}
