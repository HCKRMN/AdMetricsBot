package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YclientsAddKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Objects;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsStateHandler implements StateHandler {
    MessageWithReturn messageWithReturn;
    TempDataSaver tempDataSaver;
    YclientsAddKeyboard yclientsAddKeyboard;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, StateEnum.SETTINGS_PROJECT_ADD_YCLIENTS_STATE.getStateName())
                || Objects.equals(userStateEnum, StateEnum.REGISTRATION_ADD_YCLIENTS_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState, int messageId) {
        tempDataSaver.tempLastMessageId(chatId, messageId);
        return messageWithReturn.sendMessage(
                chatId,
                MessageEnum.ADD_YCLIENTS_STEP_2_MESSAGE.getMessage(),
                yclientsAddKeyboard.addYclientsMenu(chatId, userState),
                null);
    }
}
