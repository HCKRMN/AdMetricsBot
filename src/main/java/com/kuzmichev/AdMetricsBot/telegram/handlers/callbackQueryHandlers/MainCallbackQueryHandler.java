package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MainCallbackQueryHandler {
    CallbackHandlersList CallbackHandlersList;
    UserRepository userRepository;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String data = buttonQuery.getData();
        // Найти подходящий обработчик по значению data
        for (CallbackHandler handler : CallbackHandlersList.getCallbackHandlers()) {
            if (handler.canHandle(data)) {
                String chatId = buttonQuery.getMessage().getChatId().toString();
                String userState = userRepository.getUserStateByChatId(chatId);
                return handler.handleCallback(buttonQuery, userState);
            }
        }

        // Вернуть дефолтный ответ или обработать неизвестный колбэк
        return handleUnknownCallback(buttonQuery);
    }

    // Обработка неизвестного колбэка
    private BotApiMethod<?> handleUnknownCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
