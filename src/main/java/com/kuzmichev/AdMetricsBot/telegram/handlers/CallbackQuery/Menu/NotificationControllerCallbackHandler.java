package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.Menu;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboards;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.NotificationController;
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
public class NotificationControllerCallbackHandler implements CallbackHandler {
    NotificationController notificationController;
    MessageWithReturn messageWithReturn;
    InlineKeyboards inlineKeyboards;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, CallBackEnum.ENABLE_NOTIFICATIONS_CALLBACK.getCallBackName()) ||
                Objects.equals(data, CallBackEnum.DISABLE_NOTIFICATIONS_CALLBACK.getCallBackName());
    }

    @Override
        public BotApiMethod<?> handleCallback (CallbackQuery buttonQuery){
            String chatId = buttonQuery.getMessage().getChatId().toString();
            String data = buttonQuery.getData();
            int messageId = buttonQuery.getMessage().getMessageId();

            if (Objects.equals(data, CallBackEnum.ENABLE_NOTIFICATIONS_CALLBACK.getCallBackName())) {
                notificationController.enableNotifications(chatId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                        null,
                        inlineKeyboards.settingsMenu(chatId));
            } else if (Objects.equals(data, CallBackEnum.DISABLE_NOTIFICATIONS_CALLBACK.getCallBackName())) {
                notificationController.disableNotifications(chatId);
                return messageWithReturn.editMessage(
                        chatId,
                        messageId,
                        BotMessageEnum.SETTINGS_MENU_MESSAGE.getMessage(),
                        null,
                        inlineKeyboards.settingsMenu(chatId));
            }
            return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
    }
