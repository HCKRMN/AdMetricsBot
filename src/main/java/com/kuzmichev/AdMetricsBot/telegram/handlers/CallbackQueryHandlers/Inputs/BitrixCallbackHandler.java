package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Inputs;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards.BackAndExitMenu;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.CallbackHandler;
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
public class BitrixCallbackHandler implements CallbackHandler {
    TempDataSaver tempDataSaver;
    MessageWithReturn messageWithReturn;
    BackAndExitMenu backAndExitMenu;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.ADD_BITRIX_STEP_1_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, CallBackEnum.ADD_BITRIX_STEP_1_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.ADD_BITRIX_STEP_1_MESSAGE.getMessage(),
                    UserStateEnum.SETTINGS_PROJECT_ADD_BITRIX_STATE,
                    backAndExitMenu.backAndExitMenu(chatId));
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
