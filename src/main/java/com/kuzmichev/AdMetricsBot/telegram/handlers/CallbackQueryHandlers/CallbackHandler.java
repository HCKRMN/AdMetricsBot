package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackHandler {
    boolean canHandle(String data);
    BotApiMethod<?> handleCallback(CallbackQuery buttonQuery);

}
