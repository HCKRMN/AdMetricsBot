package com.kuzmichev.AdMetricsBot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class YaController {
@RequestMapping(value = "/yandex")
    public String getYaToken(@RequestParam("access_token") String yaToken,
                             @RequestParam("state") String chatId) {
        System.out.println(chatId);
        System.out.println(yaToken);
        return "yandex";
    }
}