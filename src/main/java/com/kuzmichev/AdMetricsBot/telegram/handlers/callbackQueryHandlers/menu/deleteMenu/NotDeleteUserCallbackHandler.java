package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.deleteMenu;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class NotDeleteUserCallbackHandler implements CallbackHandler {
    DoneButtonKeyboard doneButtonKeyboard;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();

        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.NOT_DELETE_USER_DATA_MESSAGE.getMessage())
                .replyMarkup(doneButtonKeyboard.getKeyboard(chatId, userState))
                .build();
    }
}