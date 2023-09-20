package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.registration;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationCallbackEnum;
import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalMessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.TimeZoneKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.Registration;
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
public class RegistrationCallbackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    Registration registration;
    TimeZoneKeyboard timeZoneKeyboard;
    TempDataSaver tempDataSaver;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, RegistrationCallbackEnum.START_REGISTRATION_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        String userName = buttonQuery.getMessage().getFrom().getUserName();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, RegistrationCallbackEnum.START_REGISTRATION_CALLBACK.getCallBackName())) {
            registration.registerUser(chatId, userName);
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    UniversalMessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage(),
                    null,
                    timeZoneKeyboard.timeZoneKeyboard(chatId, userState)
            );
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
