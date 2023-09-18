package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.AddYandexTestKeyboard;
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
    private final AddYandexTestKeyboard addYandexTestKeyboard;
    private final MessageManagementService messageManagementService;

    public YaController2(YandexRepository yandexRepository, TempDataRepository tempDataRepository, MessageWithoutReturn messageWithoutReturn, AddYandexTestKeyboard addYandexTestKeyboard, MessageManagementService messageManagementService) {
        this.yandexRepository = yandexRepository;
        this.tempDataRepository = tempDataRepository;
        this.messageWithoutReturn = messageWithoutReturn;
        this.addYandexTestKeyboard = addYandexTestKeyboard;
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
                SettingsMessageEnum.ADD_YANDEX_TEST_MESSAGE.getMessage(),
                addYandexTestKeyboard.addYandexTestMenu());

        log.info("Пользователь с Id: {} добавил аккаунт Yandex", chatId);
        return "yandex-r";
    }
}
