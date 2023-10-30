package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
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
    BackAndExitKeyboard backAndExitKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        InlineKeyboardMarkup backAndExitButtons = backAndExitKeyboard.backAndExitMenu(userState);
        String state = StateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE.getStateName();

        if(userState.equals(StateEnum.REGISTRATION_STATE.getStateName())){
            backAndExitButtons = null;
            state = StateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName();
        }

        if (Objects.equals(data, CallBackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    MessageEnum.PROJECT_CREATE_ASK_NAME_MESSAGE.getMessage(),
                    state,
                    backAndExitButtons);
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
