package com.kuzmichev.AdMetricsBot.telegram.handlers.MessageHandlers.MessageStateHandler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface StateHandler {
    boolean canHandle(String userStateEnum);
    BotApiMethod<?> handleState(String chatId, String messageText);
}

