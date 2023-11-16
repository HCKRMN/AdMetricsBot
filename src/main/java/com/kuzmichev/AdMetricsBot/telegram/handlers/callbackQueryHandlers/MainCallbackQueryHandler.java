package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
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
    UserStateKeeper userStateKeeper;
    MessageManagementService messageManagementService;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String data = buttonQuery.getData();
        String chatId = buttonQuery.getMessage().getChatId().toString();


        int messageId111 = buttonQuery.getMessage().getMessageId();

        System.out.println(messageId111);
        System.out.println(messageId111);
        System.out.println(messageId111);
        System.out.println(messageId111);
        System.out.println(messageId111);
        System.out.println(messageId111);


        messageManagementService.putMessageToQueue(chatId, messageId111);


        // Найти подходящий обработчик по значению data
        for (CallbackHandler handler : CallbackHandlersList.getCallbackHandlers()) {
            if (handler.canHandle(data)) {
                String userState = userStateKeeper.getState(chatId);
                return handler.handleCallback(buttonQuery, userState);
            }
        }

        // Вернуть дефолтный ответ или обработать неизвестный колбэк
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
