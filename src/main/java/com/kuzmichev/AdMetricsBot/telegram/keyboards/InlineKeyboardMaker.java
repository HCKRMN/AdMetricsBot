package com.kuzmichev.AdMetricsBot.telegram.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class InlineKeyboardMaker {

    public InlineKeyboardButton addButton(String buttonName, String callBackData, String link) {

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(callBackData);
        button.setUrl(link);

        return button;
    }
    public List<InlineKeyboardButton> addRow(InlineKeyboardButton... buttons) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        Collections.addAll(row, buttons);
        return row;
    }
    @SafeVarargs
    public final List<List<InlineKeyboardButton>> addRows(List<InlineKeyboardButton>... rows) {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        Collections.addAll(rowsInLine, rows);
        return rowsInLine;
    }

    public InlineKeyboardMarkup addMarkup(List<List<InlineKeyboardButton>> keyboardButtonsRows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboardButtonsRows);
        return markup;
    }
}