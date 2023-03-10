package com.homebrewtify.demo.contorller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    public TestController(){}


    @ResponseBody
    @RequestMapping("")
    public String getAll(){
        System.out.println("테스트");

        return "Success Test";
    }

}
