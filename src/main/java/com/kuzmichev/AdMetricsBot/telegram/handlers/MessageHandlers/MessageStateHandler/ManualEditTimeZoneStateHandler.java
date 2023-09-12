package com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.BackAndExitMenu;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TimeZoneDefinition;
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
public class ManualEditTimeZoneStateHandler implements StateHandler {
    Validator validator;
    MessageWithoutReturn messageWithoutReturn;
    BackAndExitMenu backAndExitMenu;
    TimeZoneDefinition timeZoneDefinition;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, UserStateEnum.EDIT_TIMEZONE_MANUAL_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText) {

        if (validator.validateTime(messageText)) {
            return timeZoneDefinition.manualTimeZone(chatId, messageText);
        } else {
            messageWithoutReturn.sendMessage(
                    chatId,
                    BotMessageEnum.INVALID_TIME_MESSAGE.getMessage(),
                    backAndExitMenu.backAndExitMenu(chatId));
            return null;
        }
    }
}
