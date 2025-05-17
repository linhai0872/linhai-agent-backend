package com.linhai.linhai_agent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//本接口用于测试knife4j接口文档

@RestController
@RequestMapping("/linhai")
public class LinhaiTest {

    @GetMapping("/test")
    public String test(){
        return "linhai-agent";
    }
}
