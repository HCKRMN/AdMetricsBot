package com.kuzmichev.AdMetricsBot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PrivacyController {
    @RequestMapping(value = "/privacy")
    public String getPage() {
        return "privacy";
    }
}
