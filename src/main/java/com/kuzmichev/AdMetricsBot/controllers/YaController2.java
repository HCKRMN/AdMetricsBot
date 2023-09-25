package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YandexTestKeyboard;
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
    private final YandexTestKeyboard yandexTestKeyboard;
    private final MessageManagementService messageManagementService;

    public YaController2(YandexRepository yandexRepository,
                         TempDataRepository tempDataRepository,
                         MessageWithoutReturn messageWithoutReturn,
                         YandexTestKeyboard yandexTestKeyboard,
                         MessageManagementService messageManagementService) {
        this.yandexRepository = yandexRepository;
        this.tempDataRepository = tempDataRepository;
        this.messageWithoutReturn = messageWithoutReturn;
        this.yandexTestKeyboard = yandexTestKeyboard;
        this.messageManagementService = messageManagementService;
    }

    @RequestMapping(value = "/yandex-r")
    public String getYaToken(@RequestParam(name = "access_token") String yaToken,
                             @RequestParam(name = "state") String chatId_userState){

        String[] parts = chatId_userState.split("_", 2);
        String chatId;
        String userState;

        if (parts.length == 2) {
            chatId = parts[0];
            userState = parts[1];

            TempData tempData = tempDataRepository.getByChatId(chatId);

            String projectId = tempData.getLastProjectId();
            Yandex yandex = new Yandex();
            yandex.setChatId(chatId);
            yandex.setYandexToken(yaToken);
            yandex.setProjectId(projectId);
            yandexRepository.save(yandex);

            int messageId = tempData.getLastMessageId();
            messageManagementService.putMessageToQueue(chatId, messageId);
            messageManagementService.deleteMessage(chatId);

            if (userState.equals(RegistrationStateEnum.REGISTRATION_PROJECT_ADD_TOKENS_STATE.getStateName())) {
                messageWithoutReturn.sendMessage(
                        chatId,
                        RegistrationMessageEnum.REGISTRATION_YANDEX_TEST_MESSAGE.getMessage(),
                        yandexTestKeyboard.yandexTestMenu(userState));

            } else {
                messageWithoutReturn.sendMessage(
                        chatId,
                        SettingsMessageEnum.ADD_YANDEX_TEST_MESSAGE.getMessage(),
                        yandexTestKeyboard.yandexTestMenu(userState));
            }
            log.info("Пользователь {} добавил аккаунт Yandex", chatId);

        }
        else {
            log.info("Ошибка обработки chatId_userState: {}", chatId_userState);
        }
        return "yandex-r";
    }
}