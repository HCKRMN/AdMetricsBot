package com.kuzmichev.AdMetricsBot.telegram;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.MainCallbackQueryHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageHandler;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempDataSaver;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Slf4j
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdMetricsBot extends SpringWebhookBot {
    String botPath;
    String botUsername;
    String botToken;
    MessageHandler messageHandler;
    MainCallbackQueryHandler mainCallbackQueryHandler;
    TempDataSaver tempDataSaver;

    public AdMetricsBot(SetWebhook setWebhook, MessageHandler messageHandler, MainCallbackQueryHandler mainCallbackQueryHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.mainCallbackQueryHandler = mainCallbackQueryHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new SendMessage(update.getMessage().getChatId().toString(),
                    MessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
        } catch (Exception e) {
            log.error(e.toString());
            return new SendMessage(update.getMessage().getChatId().toString(),
                    MessageEnum.EXCEPTION_WHAT_THE_FUCK.getMessage());
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return mainCallbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            if (update.getMessage() != null) {
                return messageHandler.answerMessage(update.getMessage());
            }
        }
        String chatId = update.getMessage().getChatId().toString();
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}