package com.kuzmichev.AdMetricsBot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class YaController {
    @RequestMapping(value = "/yandex")
    public String getYaToken() {

        return "yandex";
    }
}
