package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.SettingsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
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
public class BackAndExitCallbackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    SettingsKeyboard settingsKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, SettingsCallBackEnum.SETTINGS_BACK_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.NOT_DELETE_PROJECT_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, SettingsCallBackEnum.SETTINGS_BACK_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                    SettingsStateEnum.SETTINGS_EDIT_STATE.getStateName(),
                    settingsKeyboard.settingsMenu(chatId));
        } else if(Objects.equals(data, SettingsCallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.NOT_DELETE_PROJECT_CALLBACK.getCallBackName())) {
            return messageWithReturn.deleteMessage(
                    chatId,
                    messageId,
                    SettingsStateEnum.WORKING_STATE.getStateName());
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}