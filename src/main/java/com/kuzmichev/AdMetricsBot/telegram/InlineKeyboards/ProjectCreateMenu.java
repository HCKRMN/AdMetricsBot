package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectCreateMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    // Это кнопки меню второго уровня, оно универсальное. Подходит для меню проектов, настройки времени
    public InlineKeyboardMarkup projectCreateMenu() {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Кнопка назад
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                                        CallBackEnum.PROJECTS_CALLBACK.getCallBackName(),
                                        null
                                ),
                                // Кнопка выход
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );
    }
}
