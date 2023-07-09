package com.kuzmichev.AdMetricsBot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class YaController {
    @RequestMapping(value = "/yandex")
    public String getYaToken() {

        return "yandex";
    }
}
