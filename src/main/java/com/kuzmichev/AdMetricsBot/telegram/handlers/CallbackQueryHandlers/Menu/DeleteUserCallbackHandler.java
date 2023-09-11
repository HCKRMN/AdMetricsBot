package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Menu;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
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
    InlineKeyboards inlineKeyboards;
    DeleteUserData deleteUserData;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, CallBackEnum.DELETE_USER_STEP_1_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.DELETE_USER_DATA_ASK_MESSAGE.getMessage(),
                    null,
                    inlineKeyboards.deleteUserDataMenu());
        } else if(Objects.equals(data, CallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName())) {
            deleteUserData.deleteUserData(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.DELETE_USER_DATA_ASK_MESSAGE.getMessage(),
                    null,
                    inlineKeyboards.done());
        } else if(Objects.equals(data, CallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.NOT_DELETE_USER_DATA_MESSAGE.getMessage(),
                    UserStateEnum.WORKING_STATE,
                    inlineKeyboards.done());
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}