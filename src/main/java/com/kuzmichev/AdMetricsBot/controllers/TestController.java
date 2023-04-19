package com.kuzmichev.AdMetricsBot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @RequestMapping(value = "/hello")
    public String hello(@RequestParam("name") String name) {
        System.out.println(name);
                return "hello";
    }
}