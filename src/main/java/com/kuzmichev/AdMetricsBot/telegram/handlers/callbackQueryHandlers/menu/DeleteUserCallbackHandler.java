package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DeleteUserDataKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.DeleteUserData;
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
public class DeleteUserCallbackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    DeleteUserDataKeyboard deleteUserDataKeyboard;
    DeleteUserData deleteUserData;
    DoneButtonKeyboard doneButtonKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, SettingsCallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, SettingsCallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.DELETE_USER_DATA_ASK_MESSAGE.getMessage(),
                    null,
                    deleteUserDataKeyboard.deleteUserDataMenu());
        } else if(Objects.equals(data, SettingsCallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName())) {
            deleteUserData.deleteUserData(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.DELETE_USER_DATA_ASK_MESSAGE.getMessage(),
                    null,
                    doneButtonKeyboard.doneButtonMenu());
        } else if(Objects.equals(data, SettingsCallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.NOT_DELETE_USER_DATA_MESSAGE.getMessage(),
                    SettingsStateEnum.WORKING_STATE.getStateName(),
                    doneButtonKeyboard.doneButtonMenu());
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}