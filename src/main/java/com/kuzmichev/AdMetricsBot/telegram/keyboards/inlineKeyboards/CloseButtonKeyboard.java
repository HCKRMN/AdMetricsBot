package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
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
public class CloseButtonKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    // Кнопка Готово
    public InlineKeyboardMarkup closeButtonKeyboard() {
        return inlineKeyboardMaker.addMarkup(
                    inlineKeyboardMaker.addRow(
                            inlineKeyboardMaker.addButton(
                                    SettingsButtonEnum.CLOSE_BUTTON.getButtonName(),
                                    SettingsCallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                    null
                            )
                    )
        );
    }
}
