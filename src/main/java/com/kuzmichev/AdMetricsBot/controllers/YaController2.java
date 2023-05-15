package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.model.YaData;
import com.kuzmichev.AdMetricsBot.model.YaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class YaController2 {
    @Autowired
    private YaRepository yaRepository;

    @RequestMapping(value = "/yandex-r")
    public String getYaToken(@RequestParam(name = "access_token", required = false) String yaToken,
                             @RequestParam(name = "state", required = false) String chatId
//            ,                @RequestParam(name = "yaLogin", required = false) String ???)
    ){
        System.out.println(chatId);
//        System.out.println(yaLogin);
        System.out.println(yaToken);

        YaData yaData = new YaData();
        yaData.setChatId(chatId);
        yaData.setYaToken(yaToken);
        yaRepository.save(yaData);
        log.info("yaData saved: " + yaData);

        return "yandex-r";
    }
}
