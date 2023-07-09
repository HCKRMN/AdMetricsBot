package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DeleteUserData {
    BitrixRepository bitrixRepository;
    ScheduledMessageRepository scheduledMessageRepository;
    UserRepository userRepository;
    YandexRepository yandexRepository;
    ProjectRepository projectRepository;
    TempDataRepository tempDataRepository;

    public void deleteUserData(String chatId) {
        userRepository.removeUserByChatId(chatId);
        bitrixRepository.removeBitrixByChatId(chatId);
        scheduledMessageRepository.removeScheduledMessageByChatId(chatId);
        yandexRepository.removeYandexByChatId(chatId);
        projectRepository.removeProjectsByChatId(chatId);
        tempDataRepository.removeTempDataByChatId(chatId);
        log.info("Пользователь с id: {} удален", chatId);
    }
//    public SendMessage askDeleteUserData(String chatId) {
//        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.DELETE_USER_DATA_ASK_MESSAGE.getMessage());
//
//        sendMessage.setReplyMarkup(
//                inlineKeyboardMaker.addMarkup(
//                        inlineKeyboardMaker.addRows(
//                                inlineKeyboardMaker.addRow(
//                                        inlineKeyboardMaker.addButton(
//                                                ButtonNameEnum.DELETE_USER_DATA_BUTTON.getButtonName(),
//                                                CallBackEnum.DELETE_USER_STEP_2_CALLBACK,
//                                                null
//                                        ),
//                                        inlineKeyboardMaker.addButton(
//                                                ButtonNameEnum.NOT_DELETE_USER_DATA_BUTTON.getButtonName(),
//                                                CallBackEnum.NOT_DELETE_USER_CALLBACK,
//                                                null
//                                        )
//                                )
//                        )
//                )
//        );
//        log.info("Пользователь с id: {} думает удалиться", chatId);
//
//        return sendMessage;
//    }
//
//        // Изменяем статус юзера на рабочий
//    public SendMessage notDeleteUser (String chatId) {
//        userStateEditor.editUserState(chatId, UserStateEnum.WORKING_STATE);
//        return new SendMessage(chatId, BotMessageEnum.NOT_DELETE_USER_DATA_MESSAGE.getMessage());
//    }
}
