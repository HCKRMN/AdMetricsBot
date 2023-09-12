package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Inputs;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.AddYandexMenu;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.AddYandexTestMenu;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.AddYandex;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
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
public class YandexCallbackHandler implements CallbackHandler {
    TempDataSaver tempDataSaver;
    MessageWithReturn messageWithReturn;
    AddYandexTestMenu addYandexTestMenu;
    AddYandex addYandex;
    AddYandexMenu addYandexMenu;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, CallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.ADD_YANDEX_MESSAGE.getMessage(),
                    UserStateEnum.SETTINGS_PROJECT_ADD_YANDEX_STATE,
                    addYandexMenu.addYandexMenu(chatId));
        } else if (Objects.equals(data, CallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    addYandex.testYandex(chatId),
                    UserStateEnum.SETTINGS_PROJECT_ADD_YANDEX_STATE,
                    addYandexTestMenu.addYandexTestMenu());
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
