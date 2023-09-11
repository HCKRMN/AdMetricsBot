package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MainCallbackQueryHandler {
    CallbackHandlersList CallbackHandlersList;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String data = buttonQuery.getData();
        // Найти подходящий обработчик по значению data
        for (CallbackHandler handler : CallbackHandlersList.getCallbackHandlers()) {
            if (handler.canHandle(data)) {
                return handler.handleCallback(buttonQuery);
            }
        }

        // Вернуть дефолтный ответ или обработать неизвестный колбэк
        return handleUnknownCallback(buttonQuery);
    }

    // Обработка неизвестного колбэка
    private BotApiMethod<?> handleUnknownCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
