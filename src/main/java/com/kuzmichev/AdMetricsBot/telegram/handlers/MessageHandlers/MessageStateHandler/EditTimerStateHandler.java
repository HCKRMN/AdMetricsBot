package com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddTimer;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.ProjectManager;
import com.kuzmichev.AdMetricsBot.telegram.utils.UserStateEditor;
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
    MessageWithoutReturn messageWithoutReturn;
    InlineKeyboards inlineKeyboards;
    AddTimer addTimer;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, UserStateEnum.SETTINGS_EDIT_TIMER_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText) {

        if (validator.validateTime(messageText)) {
            return addTimer.setTimerAndStart(chatId, messageText);
        } else {
            messageWithoutReturn.sendMessage(
                    chatId,
                    BotMessageEnum.INVALID_TIME_MESSAGE.getMessage(),
                    inlineKeyboards.backAndExitMenu(chatId));
            return null;
        }
    }
}
