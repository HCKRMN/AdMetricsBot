//package com.kuzmichev.AdMetricsBot.telegram.utils;
//
//import com.kuzmichev.AdMetricsBot.constants.MenuEnums.SettingsMessageEnum;
//import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
//import com.kuzmichev.AdMetricsBot.constants.MenuEnums.SettingsCallBackEnum;
//import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.InlineKeyboardMaker;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//
//@Slf4j
//@Component
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@RequiredArgsConstructor
//public class StartCommandReceived {
//    InlineKeyboardMaker inlineKeyboardMaker;
//
//    /**
//     * Отправляет приветственное сообщение пользователю.
//     *
//     * @param chatId    идентификатор чата
//     * @param firstName имя пользователя
//     * @return приветственное сообщение
//     */
//    public SendMessage sendGreetingMessage(String chatId, String firstName) {
//        SendMessage sendMessage = new SendMessage(chatId, SettingsMessageEnum.START_MESSAGE.getMessage());
//
//        sendMessage.setReplyMarkup(
//                inlineKeyboardMaker.addMarkup(
//                        inlineKeyboardMaker.addRows(
//                                inlineKeyboardMaker.addRow(
//                                        inlineKeyboardMaker.addButton(
//                                                SettingsButtonEnum.REGISTRATION_BUTTON.getButtonName(),
//                                                SettingsCallBackEnum.START_REGISTRATION_CALLBACK.getCallBackName(),
//                                                null
//                                        )
//                                )
//                        )
//                )
//        );
//
//        log.info("Пользователь {} с id: {} стартует.", firstName, chatId);
//
//        return sendMessage;
//    }
//}
