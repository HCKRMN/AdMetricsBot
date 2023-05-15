package com.kuzmichev.AdMetricsBot.telegram.handlers;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.model.YaRepository;
import com.kuzmichev.AdMetricsBot.service.YandexDirectRequest;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddTokensMenu;
import com.kuzmichev.AdMetricsBot.telegram.utils.CheckYaData;
import com.kuzmichev.AdMetricsBot.telegram.utils.Registration;
import com.kuzmichev.AdMetricsBot.telegram.utils.TimeZoneDefinition;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {
    YaRepository yaRepository;
    AddTokensMenu addTokensMenu;
    Registration registration;
    TimeZoneDefinition timeZoneDefinition;
    CheckYaData checkYaData;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String userName = buttonQuery.getMessage().getFrom().getUserName();
        String data = buttonQuery.getData();

        switch (data) {
            case "START_REGISTRATION" -> {
                registration.registerUser(chatId, userName);
                return timeZoneDefinition.requestTimeZoneSettingLink(chatId);
            }
            case "ADD_TOKENS" -> {
                return addTokensMenu.addTokens(chatId);
            }
            case "ADD_YANDEX" -> {
                 return checkYaData.findYaData(chatId);
            }
            case "NO_CONTINUE" -> {
                return new SendMessage(chatId, BotMessageEnum.ASK_TIME_MESSAGE.getMessage());
            }
            case "TEST_YA" ->{              ///////////// Проверить ошибку и дописать лог
                try {
                return new SendMessage(chatId, BotMessageEnum.YANDEX_RESOULT_MESSAGE.getMessage() + YandexDirectRequest.ya(yaRepository, chatId));
            } catch (Exception e) {
                    return new SendMessage(chatId, BotMessageEnum.ERRORE_YANDEX_MESSAGE.getMessage());
                }
            }
            default -> {}
        }
        return null;
    }
}