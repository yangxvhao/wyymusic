package com.example.demo;

import com.example.demo.pipeline.NetEaseMusicPipeline;
import com.example.demo.processor.WangyiMusicPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yangxvhao on 17-8-31.
 */

//@SpringBootApplication
//@RestController
public class WebApplication {

    @Autowired
    WangyiMusicPageProcessor wangyiMusicPageProcessor;

    @Autowired
    NetEaseMusicPipeline netEaseMusicPipeline;

    @RequestMapping("/hello")
    String hello() {
        return "Hello World!!!";
    }

    @RequestMapping("/start")
    String start(){
        new Thread(()->wangyiMusicPageProcessor.start(wangyiMusicPageProcessor,netEaseMusicPipeline)).start();
        return "sucess";
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}
