package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
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
    BackAndExitKeyboard backAndExitKeyboard;
    TimeZoneDefinition timeZoneDefinition;

    @Override
    public boolean canHandle(String userStateEnum) {
        return Objects.equals(userStateEnum, SettingsStateEnum.EDIT_TIMEZONE_MANUAL_STATE.getStateName());
    }

    @Override
    public BotApiMethod<?> handleState(String chatId, String messageText, String userState) {

        if (validator.validateTime(messageText)) {
            return timeZoneDefinition.manualTimeZone(chatId, messageText);
        } else {
            messageWithoutReturn.sendMessage(
                    chatId,
                    SettingsMessageEnum.INVALID_TIME_MESSAGE.getMessage(),
                    backAndExitKeyboard.backAndExitMenu(userState));
            return null;
        }
    }
}
