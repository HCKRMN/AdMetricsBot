package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.*;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StartCommandReceived {
    InlineKeyboardMaker inlineKeyboardMaker;

    /**
     * Отправляет приветственное сообщение пользователю.
     *
     * @param chatId    идентификатор чата
     * @param firstName имя пользователя
     * @return приветственное сообщение
     */
    public SendMessage sendGreetingMessage(String chatId, String firstName) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.START_MESSAGE.getMessage());

        sendMessage.setReplyMarkup(
                inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(
                                inlineKeyboardMaker.addRow(
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.REGISTRATION_BUTTON.getButtonName(),
                                                CallBackEnum.START_REGISTRATION_CALLBACK.getCallBackName(),
                                                null
                                        )
                                )
                        )
                )
        );

        log.info("Пользователь {} с id: {} стартует.", firstName, chatId);

        return sendMessage;
    }
}
