//package com.kuzmichev.AdMetricsBot.telegram.utils;
//
//import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
//import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
//import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
//import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.ArrayList;
//import java.util.List;
//@Slf4j
//@Component
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@RequiredArgsConstructor
//public class AddTokensMenu {
//    InlineKeyboardMaker inlineKeyboardMaker;
//
//    public SendMessage addTokens(String chatId) {
//        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ADD_TOKENS_MESSAGE.getMessage());
//
//
//        sendMessage.setReplyMarkup(
//                inlineKeyboardMaker.addMarkup(
//                        inlineKeyboardMaker.addRows(
//                                inlineKeyboardMaker.addRow(
//                                        inlineKeyboardMaker.addButton(
//                                                ButtonNameEnum.ADD_YANDEX_BUTTON.getButtonName(),
//                                                CallBackEnum.ADD_YANDEX_CALLBACK,
//                                                null
//                                        )
//                                )
//                        )
//                )
//        );
//
//        log.info("Пользователь id: {} Подключает Яндекс.", chatId);
//        return sendMessage;
//    }
//}