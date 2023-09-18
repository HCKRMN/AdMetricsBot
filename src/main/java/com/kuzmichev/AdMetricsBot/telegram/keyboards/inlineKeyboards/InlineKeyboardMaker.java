package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import java.util.stream.Collectors;

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
        return Arrays.stream(buttons)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Создает и возвращает разметку с кнопками. Обычный метод с получением сетки кнопок
     *
     * @param keyboardButtonsRows сетка из кнопок
     * @return созданная разметка с кнопками
     */
    public InlineKeyboardMarkup addMarkup(List<List<InlineKeyboardButton>> keyboardButtonsRows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboardButtonsRows);
        return markup;
    }

    /**
     * Создает и возвращает разметку с кнопками. Объединенный метод с получением строк кнопок
     *
     * @param rows строки кнопок
     * @return созданная разметка с кнопками
     */
    @SafeVarargs
    public final InlineKeyboardMarkup addMarkup(List<InlineKeyboardButton>... rows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Arrays.stream(rows)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        return markup;
    }




}