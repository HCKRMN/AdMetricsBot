package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalCallbackEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
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
public class EditTimerCallbackHandler implements CallbackHandler {
    TempDataSaver tempDataSaver;
    MessageWithReturn messageWithReturn;
    BackAndExitKeyboard backAndExitKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, UniversalCallbackEnum.UNIVERSAL_EDIT_TIMER_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, UniversalCallbackEnum.UNIVERSAL_EDIT_TIMER_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.ASK_TIME_MESSAGE.getMessage(),
                    SettingsStateEnum.SETTINGS_EDIT_TIMER_STATE.getStateName(),
                    backAndExitKeyboard.backAndExitMenu(userState));
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
