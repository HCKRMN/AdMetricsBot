package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YclientsAddKeyboard;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsStateHandler implements StateHandler {
    YclientsAddKeyboard yclientsAddKeyboard;

    @Override
    public boolean canHandle(String userStateEnum) {
        return userStateEnum.equals(StateEnum.SETTINGS_ADD_YCLIENTS_STATE.getStateName())
                || userStateEnum.equals(StateEnum.REGISTRATION_ADD_YCLIENTS_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {
        if (messageText == null){
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.ADD_YCLIENTS_STEP_2_MESSAGE.getMessage())
                    .replyMarkup(yclientsAddKeyboard.getKeyboard(userState, chatId))
                    .build();

        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.PHONE_INPUT_ERROR_MESSAGE.getMessage())
                    .build();
        }
    }
}
