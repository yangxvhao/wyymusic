package com.example.wangyiyun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yangxvhao on 17-8-9.
 */
@Controller
public class demoController {

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "hello world!";
    }
}
