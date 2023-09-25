package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.AddYandexKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.YandexTestKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
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
    YandexTestKeyboard yandexTestKeyboard;
    AddYandex addYandex;
    AddYandexKeyboard addYandexKeyboard;
    TempDataRepository tempDataRepository;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, SettingsCallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName())
                || Objects.equals(data, SettingsCallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, SettingsCallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.ADD_YANDEX_MESSAGE.getMessage(),
                    SettingsStateEnum.SETTINGS_PROJECT_ADD_YANDEX_STATE.getStateName(),
                    addYandexKeyboard.addYandexMenu(chatId, userState));
        } else if (Objects.equals(data, SettingsCallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName())) {
            String projectId = tempDataRepository.findLastProjectIdByChatId(chatId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    addYandex.testYandex(projectId),
                    SettingsStateEnum.SETTINGS_PROJECT_ADD_YANDEX_STATE.getStateName(),
                    yandexTestKeyboard.yandexTestMenu(userState));
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
