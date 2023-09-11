package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Menu;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.CallbackHandler;
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
public class EditLanguageCallbackHandler  implements CallbackHandler {

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.EDIT_LANGUAGE_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();

        if (Objects.equals(data, CallBackEnum.EDIT_LANGUAGE_CALLBACK.getCallBackName())) {
            return new SendMessage(
                    chatId,
                    BotMessageEnum.IN_DEVELOPING_MESSAGE.getMessage());
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}