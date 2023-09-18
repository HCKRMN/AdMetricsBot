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
public class AddYandexTestKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;

    public InlineKeyboardMarkup addYandexTestMenu() {
        return inlineKeyboardMaker.addMarkup(
                        //Кнопка тестового запроса
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.TEST_YANDEX_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка добавить другие токены
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.SETTINGS_ADD_ACCOUNTS_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка готово
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.DONE_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                                        null
                                )
                        )

        );
    }
}