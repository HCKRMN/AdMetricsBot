package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddYandex;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class YaController2 {
    private final YandexRepository yandexRepository;
    private final TempDataRepository tempDataRepository;
    private final MessageWithoutReturn messageWithoutReturn;
    private final InlineKeyboards inlineKeyboards;
    private final MessageManagementService messageManagementService;

    public YaController2(YandexRepository yandexRepository, TempDataRepository tempDataRepository, MessageWithoutReturn messageWithoutReturn, InlineKeyboards inlineKeyboards, MessageManagementService messageManagementService) {
        this.yandexRepository = yandexRepository;
        this.tempDataRepository = tempDataRepository;
        this.messageWithoutReturn = messageWithoutReturn;
        this.inlineKeyboards = inlineKeyboards;
        this.messageManagementService = messageManagementService;
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

        int messageId = tempDataRepository.findLastMessageIdByChatId(chatId);
        messageManagementService.putMessageToQueue(chatId, messageId);
        messageManagementService.deleteMessage(chatId);

        messageWithoutReturn.sendMessage(
                chatId,
                BotMessageEnum.ADD_YANDEX_TEST_MESSAGE.getMessage(),
                inlineKeyboards.addYandexTestMenu());

        log.info("Пользователь с Id: {} добавил аккаунт Yandex", chatId);
        return "yandex-r";
    }
}
