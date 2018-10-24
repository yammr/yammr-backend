package com.gibgab.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceTestController {

    @RequestMapping("/test")
    public String index(){
        return "I'm alive";
    }
}
