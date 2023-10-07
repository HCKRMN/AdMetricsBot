package com.kuzmichev.AdMetricsBot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EulaController {
    @RequestMapping(value = "/eula")
    public String getPage() {
        return "eula";
    }
}
