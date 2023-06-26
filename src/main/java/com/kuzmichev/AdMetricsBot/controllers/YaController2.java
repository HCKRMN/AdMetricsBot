package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.model.Yandex;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class YaController2 {
    @Autowired
    private YandexRepository yandexRepository;

    @RequestMapping(value = "/yandex-r")
    public String getYaToken(@RequestParam(name = "access_token", required = false) String yaToken,
                             @RequestParam(name = "state", required = false) String chatId
//            ,                @RequestParam(name = "yaLogin", required = false) String ???)
    ){
//        System.out.println(chatId);
//        System.out.println(yaLogin);
//        System.out.println(yaToken);

        Yandex yandex = new Yandex();
        yandex.setChatId(chatId);
        yandex.setYaToken(yaToken);
        yandexRepository.save(yandex);
        log.info("yandex saved: " + yandex);

        return "yandex-r";
    }
}
