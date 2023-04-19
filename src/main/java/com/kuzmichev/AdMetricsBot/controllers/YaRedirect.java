package com.kuzmichev.AdMetricsBot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class YaRedirect {

    @RequestMapping(value ="/ya-redirect")
    public String  hello() {
        return "ya-redirect";
    }
}
