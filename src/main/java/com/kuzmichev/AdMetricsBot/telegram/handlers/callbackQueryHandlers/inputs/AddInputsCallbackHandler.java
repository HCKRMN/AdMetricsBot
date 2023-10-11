package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.AddInputsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
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
public class AddInputsCallbackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    AddInputsKeyboard addInputsKeyboard;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        String newState = StateEnum.SETTINGS_PROJECT_ADD_BITRIX_STATE.getStateName();

        if (userState.equals(StateEnum.REGISTRATION_ADD_INPUTS_STATE.getStateName())) {
            newState = userState;
        }

        if (Objects.equals(data, CallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    MessageEnum.ADD_TOKENS_MESSAGE.getMessage(),
                    newState,
                    addInputsKeyboard.addTokensMenu(userState));
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
