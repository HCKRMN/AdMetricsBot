package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddTimer;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
import com.kuzmichev.AdMetricsBot.telegram.utils.Validator;
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
public class EditTimerStateHandler implements StateHandler {
    Validator validator;
    BackAndExitKeyboard backAndExitKeyboard;
    AddTimer addTimer;
    TempDataSaver tempDataSaver;
    MessageWithReturn messageWithReturn;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, StateEnum.EDIT_TIMER_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState, int messageId) {

        if (validator.validateTime(messageText)) {
            return addTimer.setTimerAndStart(chatId, messageText, userState);
        } else {
            tempDataSaver.tempLastMessageId(chatId, messageId-1);
            return messageWithReturn.sendMessage(
                    chatId,
                    MessageEnum.INVALID_TIME_MESSAGE.getMessage(),
                    backAndExitKeyboard.backAndExitMenu(userState),
                    null);
        }
    }
}
