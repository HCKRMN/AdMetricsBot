package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.CloseButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YandexTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class YandexController2 {
    YandexRepository yandexRepository;
    ProjectsDataTempKeeper projectsDataTempKeeper;
    MessageWithoutReturn messageWithoutReturn;
    YandexTestKeyboard yandexTestKeyboard;
    MessageManagementService messageManagementService;
    UserStateKeeper userStateKeeper;
    CloseButtonKeyboard closeButtonKeyboard;

    @RequestMapping(value = "/yandex-r")
    public String getYaToken(@RequestParam(name = "access_token") String yaToken,
                             @RequestParam(name = "state") String chatId_userState){

        String[] parts = chatId_userState.split("_", 2);
        String chatId;
        String userState;

        if (parts.length == 2) {
            chatId = parts[0];
            userState = parts[1];

            String projectId = projectsDataTempKeeper.getLastProjectId(chatId);

            if(projectId == null) {
                messageWithoutReturn.sendMessage(
                        chatId,
                        MessageEnum.ERROR_PROJECT_ID_NULL_MESSAGE.getMessage(),
                        closeButtonKeyboard.closeButtonKeyboard());
                return "yandexError";
            }

            Yandex yandex = Yandex.builder()
                    .chatId(chatId)
                    .yandexToken(yaToken)
                    .projectId(projectId)
                    .build();
            yandexRepository.save(yandex);

            messageManagementService.deleteMessage(chatId);

            if (userState.contains(StateEnum.REGISTRATION.getStateName())) {
                userState = StateEnum.REGISTRATION_ADD_YANDEX_TEST_STATE.getStateName();
                userStateKeeper.setState(chatId, userState);
                messageWithoutReturn.sendMessage(
                        chatId,
                        MessageEnum.REGISTRATION_TEST_INPUTS_MESSAGE.getMessage(),
                        yandexTestKeyboard.getKeyboard(chatId, userState));

            } else {
                userState = StateEnum.SETTINGS_ADD_YANDEX_TEST_STATE.getStateName();
                userStateKeeper.setState(chatId, userState);
                messageWithoutReturn.sendMessage(
                        chatId,
                        MessageEnum.INPUT_TEST_MESSAGE.getMessage(),
                        yandexTestKeyboard.getKeyboard(chatId, userState));
            }
            log.info("Пользователь {} добавил аккаунт Yandex", chatId);
        }
        else {
            log.info("Ошибка обработки chatId_userState: {}", chatId_userState);
        }
        return "yandex-r";
    }
}