package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.universal;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalMessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.TimeZoneKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Objects;
@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeZoneMenuCallbackHandler implements CallbackHandler {
    TempDataSaver tempDataSaver;
    MessageWithReturn messageWithReturn;
    TimeZoneKeyboard timeZoneKeyboard;
    BackAndExitKeyboard backAndExitKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, SettingsCallBackEnum.EDIT_TIMEZONE_LINK_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.EDIT_TIMEZONE_MANUAL_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, SettingsCallBackEnum.EDIT_TIMEZONE_LINK_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    UniversalMessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage(),
                    SettingsStateEnum.SETTINGS_EDIT_TIMEZONE_STATE.getStateName(),
                    timeZoneKeyboard.timeZoneKeyboard(chatId, userState));

        } else if (Objects.equals(data, SettingsCallBackEnum.EDIT_TIMEZONE_MANUAL_CALLBACK.getCallBackName())) {
            String newState;
            if(userState.contains("REGISTRATION")){
                newState = RegistrationStateEnum.REGISTRATION_EDIT_TIMEZONE_MANUAL_STATE.getStateName();
            } else {
                newState = SettingsStateEnum.SETTINGS_EDIT_TIMEZONE_MANUAL_STATE.getStateName();
            }
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.EDIT_TIMEZONE_MANUAL_MESSAGE.getMessage(),
                    newState,
                    backAndExitKeyboard.backAndExitMenu(userState));
        }

        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}