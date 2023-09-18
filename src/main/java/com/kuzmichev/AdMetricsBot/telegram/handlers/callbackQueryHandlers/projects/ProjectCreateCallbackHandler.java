package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalCallbackEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalMessageEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Objects;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectCreateCallbackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    TempDataSaver tempDataSaver;
    UserRepository userRepository;
    BackAndExitKeyboard backAndExitKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, UniversalCallbackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        InlineKeyboardMarkup backAndExitButtons = backAndExitKeyboard.backAndExitMenu(userState);
        String state = SettingsStateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE.getStateName();

        if(userState.equals(RegistrationStateEnum.REGISTRATION_STATE.getStateName())){
            backAndExitButtons = null;
            state = RegistrationStateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName();
        }

        if (Objects.equals(data, UniversalCallbackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    UniversalMessageEnum.PROJECT_CREATE_ASK_NAME_MESSAGE.getMessage(),
                    state,
                    backAndExitButtons);
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
