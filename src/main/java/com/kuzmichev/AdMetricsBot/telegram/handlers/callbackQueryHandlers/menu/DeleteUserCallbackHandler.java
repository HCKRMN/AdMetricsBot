package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
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
        return Objects.equals(data, CallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, CallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    MessageEnum.DELETE_USER_DATA_ASK_MESSAGE.getMessage(),
                    null,
                    deleteUserDataKeyboard.deleteUserDataMenu());
        } else if(Objects.equals(data, CallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName())) {
            deleteUserData.deleteUserData(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    MessageEnum.DELETE_USER_DATA_ASK_MESSAGE.getMessage(),
                    null,
                    doneButtonKeyboard.doneButtonMenu());
        } else if(Objects.equals(data, CallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    MessageEnum.NOT_DELETE_USER_DATA_MESSAGE.getMessage(),
                    StateEnum.WORKING_STATE.getStateName(),
                    doneButtonKeyboard.doneButtonMenu());
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}