package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class InlineKeyboardMaker {

    /**
     * Создает и возвращает кнопку с заданным названием, данными обратного вызова и ссылкой.
     *
     * @param buttonName   название кнопки
     * @param callBackData данные обратного вызова
     * @param link         ссылка
     * @return созданная кнопка
     */
    public InlineKeyboardButton addButton(String buttonName, String callBackData, String link) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(callBackData);
        button.setUrl(link);
        return button;
    }

    /**
     * Создает и возвращает ряд(столбец) кнопок.
     *
     * @param buttons кнопки в ряду
     * @return созданный ряд кнопок
     */
    public List<InlineKeyboardButton> addRow(InlineKeyboardButton... buttons) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        Collections.addAll(row, buttons);
        return row;
    }

    /**
     * Создает и возвращает несколько рядов кнопок.
     *
     * @param rows ряды кнопок
     * @return созданные ряды кнопок
     */
    @SafeVarargs
    public final List<List<InlineKeyboardButton>> addRows(List<InlineKeyboardButton>... rows) {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        Collections.addAll(rowsInLine, rows);
        return rowsInLine;
    }

    /**
     * Создает и возвращает разметку с кнопками.
     *
     * @param keyboardButtonsRows ряды кнопок
     * @return созданная разметка с кнопками
     */
    public InlineKeyboardMarkup addMarkup(List<List<InlineKeyboardButton>> keyboardButtonsRows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboardButtonsRows);
        return markup;
    }
}