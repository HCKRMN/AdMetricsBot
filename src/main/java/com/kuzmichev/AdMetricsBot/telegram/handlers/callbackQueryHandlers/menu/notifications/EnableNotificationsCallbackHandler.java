package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.notifications;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.SettingsKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.NotificationController;
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
public class EnableNotificationsCallbackHandler implements CallbackHandler {
    NotificationController notificationController;
    SettingsKeyboard settingsKeyboard;

    @Override
    public boolean canHandle(String data) {
        return data.equals(CallBackEnum.ENABLE_NOTIFICATIONS_CALLBACK.getCallBackName());
    }

    @Override
    public BotApiMethod<?> handleCallback (CallbackQuery buttonQuery, String userState){
        String chatId = buttonQuery.getMessage().getChatId().toString();
        int messageId = buttonQuery.getMessage().getMessageId();

        notificationController.enableNotifications(chatId);
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(MessageEnum.SETTINGS_MENU_MESSAGE.getMessage())
                .replyMarkup(settingsKeyboard.getKeyboard(userState, chatId))
                .build();
    }
}
