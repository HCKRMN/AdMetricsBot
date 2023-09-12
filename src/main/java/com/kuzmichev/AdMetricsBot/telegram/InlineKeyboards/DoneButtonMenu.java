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
public class DoneButtonMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    // Кнопка Готово
    public InlineKeyboardMarkup done() {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.DONE_BUTTON.getButtonName(),
                                        CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )
                )
        );
    }
}
