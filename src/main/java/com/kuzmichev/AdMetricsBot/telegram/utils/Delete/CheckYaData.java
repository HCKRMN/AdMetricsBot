//package com.kuzmichev.AdMetricsBot.telegram.utils;
//
//import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
//import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
//import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
//import com.kuzmichev.AdMetricsBot.model.Yandex;
//import com.kuzmichev.AdMetricsBot.model.YandexRepository;
//import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
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
//public class CheckYaData {
//    AddYandex addYandex;
//    YandexRepository yandexRepository;
//    InlineKeyboardMaker inlineKeyboardMaker;
//
////    public SendMessage findYaData(String chatId) {
////        Yandex yandex = yandexRepository.findById(chatId).orElse(null);
////        if (yandex != null && yandex.getYaToken() != null) {
////
////            SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ACCOUNT_ALREADY_ADDED_MESSAGE.getMessage());
////
////
////
////            sendMessage.setReplyMarkup(
////                    inlineKeyboardMaker.addMarkup(
////                            inlineKeyboardMaker.addRows(
////                                    inlineKeyboardMaker.addRow(
////                                            inlineKeyboardMaker.addButton(
////                                                    ButtonNameEnum.YES_ADD_BUTTON.getButtonName(),
////                                                    CallBackEnum.YES_ADD_CALLBACK,
////                                                    null
////                                            )
////                                    )
////                            )
////                    )
////            );
////
////
////
////
////
//////            InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
//////            List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
//////            rowsInLine.add(inlineKeyboardMaker.getButton(
//////                    ButtonNameEnum.YES_ADD_BUTTON.getButtonName(),
//////                    "YES_ADD",
//////                    null));
//////            rowsInLine.add(inlineKeyboardMaker.getButton(
//////                    ButtonNameEnum.NO_CONTINUE_BUTTON.getButtonName(),
//////                    "NO_CONTINUE",
//////                    null));
//////
//////            markupInLine.setKeyboard(rowsInLine);
//////            sendMessage.setReplyMarkup(markupInLine);
////
////            log.info("Пользователь id: {} Уже добавлял Яндекс.", chatId);
////            return sendMessage;
////        } else {
////            return addYandex.addYaData(chatId);
//////            addYaData(chatId);
//////            preTestYaData(chatId);
////        }
////    }
//
//}
