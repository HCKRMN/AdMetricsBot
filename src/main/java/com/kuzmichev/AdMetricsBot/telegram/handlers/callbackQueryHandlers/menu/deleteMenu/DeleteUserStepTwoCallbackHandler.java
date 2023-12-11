package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.deleteMenu;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.DeleteUserData;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static java.lang.Thread.sleep;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DeleteUserStepTwoCallbackHandler implements CallbackHandler {
    DeleteUserData deleteUserData;
    DoneButtonKeyboard doneButtonKeyboard;
    MessageWithoutReturn messageWithoutReturn;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) throws InterruptedException {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();

//        return EditMessageText.builder()
//                .chatId(chatId)
//                .messageId(messageId)
//                .text(MessageEnum.DELETE_USER_DATA_SUCCESS_MESSAGE.getMessage())
//                .replyMarkup(doneButtonKeyboard.getKeyboard(chatId, userState))
//                .build();
        messageWithoutReturn.deleteMessage(chatId, messageId);

        messageWithoutReturn.sendMessage(chatId, MessageEnum.DELETE_USER_DATA_SUCCESS_MESSAGE.getMessage());

        messageId++;

        deleteUserData.deleteUserData(chatId);

        sleep(5000);

        return DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
    }
}