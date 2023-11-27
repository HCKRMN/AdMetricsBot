package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface InlineKeyboard {
    InlineKeyboardMarkup getKeyboard(String chatId, String userState);
}
