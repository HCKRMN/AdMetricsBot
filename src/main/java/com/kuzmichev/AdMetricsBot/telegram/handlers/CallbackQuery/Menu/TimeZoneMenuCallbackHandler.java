package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.Menu;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
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
public class TimeZoneMenuCallbackHandler implements CallbackHandler {
    TempDataSaver tempDataSaver;
    MessageWithReturn messageWithReturn;
    InlineKeyboards inlineKeyboards;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.EDIT_TIMEZONE_LINK_CALLBACK.getCallBackName())
                || Objects.equals(data, CallBackEnum.EDIT_TIMEZONE_MANUAL_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();

        if (Objects.equals(data, CallBackEnum.EDIT_TIMEZONE_LINK_CALLBACK.getCallBackName())) {
            tempDataSaver.tempLastMessageId(chatId, messageId);
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.TIME_ZONE_DEFINITION_MESSAGE.getMessage(),
                    UserStateEnum.SETTINGS_EDIT_TIMEZONE_STATE,
                    inlineKeyboards.timeZoneMenu(chatId));
        } else if (Objects.equals(data, CallBackEnum.EDIT_TIMEZONE_MANUAL_CALLBACK.getCallBackName())) {
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    BotMessageEnum.EDIT_TIMEZONE_MANUAL_MESSAGE.getMessage(),
                    UserStateEnum.EDIT_TIMEZONE_MANUAL_STATE,
                    inlineKeyboards.backAndExitMenu(chatId));
        }
        return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}