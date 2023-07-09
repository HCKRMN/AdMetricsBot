package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class YaController2 {
    private final YandexRepository yandexRepository;
    private final TempDataRepository tempDataRepository;

    public YaController2(YandexRepository yandexRepository, TempDataRepository tempDataRepository) {
        this.yandexRepository = yandexRepository;
        this.tempDataRepository = tempDataRepository;
    }

    @RequestMapping(value = "/yandex-r")
    public String getYaToken(@RequestParam(name = "access_token", required = false) String yaToken, // Сделать обязательным потом и проверить
                             @RequestParam(name = "state", required = false) String chatId){

        String projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
        Yandex yandex = new Yandex();
        yandex.setChatId(chatId);
        yandex.setYaToken(yaToken);
        yandex.setProjectId(projectId);
        yandexRepository.save(yandex);
        log.info("Пользователь с Id: {} добавил аккаунт Yandex", chatId);

        return "yandex-r";
    }
}
