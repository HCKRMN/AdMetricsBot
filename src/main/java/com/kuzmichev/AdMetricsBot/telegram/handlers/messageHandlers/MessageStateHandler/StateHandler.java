package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers.MessageStateHandler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface StateHandler {
    boolean canHandle(String userStateEnum);
    BotApiMethod<?> handleState(String chatId, String messageText, String userStateEnum, int messageId);
}

