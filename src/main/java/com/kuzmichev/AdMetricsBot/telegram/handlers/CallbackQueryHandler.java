package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.telegram.utils.Registration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {
    Registration registration;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        long chatId = buttonQuery.getMessage().getChatId();
        String userName = buttonQuery.getMessage().getFrom().getUserName();
        String data = buttonQuery.getData();

        switch (data) {
            case "START_REGISTRATION":
                registration.registerUser(chatId, userName);
                break;
            case "ADD_YA_BUTTON":
                // return addTokens(chatId);
                break;
            case "YES_ADD":
                // return findYaData(chatId);
                break;
            case "NO_CONTINUE":
                // return askTime(chatId);
                break;
            case "TEST_YA":
                // return testYaData(chatId);
                break;
            default:
                break;
        }

        return null;
    }


}