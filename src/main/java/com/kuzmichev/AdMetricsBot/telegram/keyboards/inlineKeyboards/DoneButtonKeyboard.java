package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
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
public class DoneButtonKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    // Кнопка Готово
    public InlineKeyboardMarkup doneButtonMenu() {
        return inlineKeyboardMaker.addMarkup(
                    inlineKeyboardMaker.addRow(
                            inlineKeyboardMaker.addButton(
                                    ButtonEnum.DONE_BUTTON.getButtonName(),
                                    CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                    null
                            )
                    )
        );
    }
}
