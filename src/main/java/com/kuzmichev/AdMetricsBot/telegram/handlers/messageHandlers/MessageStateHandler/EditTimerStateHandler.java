package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddTimer;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.Validator;
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
public class EditTimerStateHandler implements StateHandler {
    Validator validator;
    BackAndExitKeyboard backAndExitKeyboard;
    AddTimer addTimer;
    UserStateKeeper userStateKeeper;

    @Override
    public boolean canHandle(String userStateEnum) {
        return userStateEnum.equals(StateEnum.SETTINGS_EDIT_TIMER_STATE.getStateName())
                || userStateEnum.equals(StateEnum.REGISTRATION_EDIT_TIMER_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {
        if (validator.validateTime(messageText)) {
            userStateKeeper.setState(chatId, StateEnum.WORKING_STATE.getStateName());
            return addTimer.setTimerAndStart(chatId, messageText, userState);

        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(MessageEnum.INVALID_TIME_MESSAGE.getMessage())
                    .replyMarkup(backAndExitKeyboard.getKeyboard(chatId, userState))
                    .build();
        }
    }
}
