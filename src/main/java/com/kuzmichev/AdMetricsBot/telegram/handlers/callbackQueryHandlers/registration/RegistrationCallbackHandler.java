package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.registration;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.Registration;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
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
public class RegistrationCallbackHandler implements CallbackHandler {
    Registration registration;
    UserStateKeeper userStateKeeper;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.START_REGISTRATION_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String userName = buttonQuery.getFrom().getUserName();
        int messageId = buttonQuery.getMessage().getMessageId();

        registration.registerUser(chatId, userName);
        userStateKeeper.setState(chatId, StateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName());
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.PROJECT_CREATE_ASK_NAME_MESSAGE.getMessage())
                .build();
    }
}
