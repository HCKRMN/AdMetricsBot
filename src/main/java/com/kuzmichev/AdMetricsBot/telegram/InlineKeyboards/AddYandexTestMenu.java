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
public class AddYandexTestMenu {
    InlineKeyboardMaker inlineKeyboardMaker;

    public InlineKeyboardMarkup addYandexTestMenu() {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        //Кнопка тестового запроса
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.TEST_YANDEX_BUTTON.getButtonName(),
                                        CallBackEnum.TEST_YANDEX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка добавить другие токены
                        inlineKeyboardMaker.addRow(
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.SETTINGS_ADD_ACCOUNTS_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        //Кнопка готово
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