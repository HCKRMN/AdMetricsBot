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
public class AddTokensMenu {
    InlineKeyboardMaker inlineKeyboardMaker;
    BackAndExitMenu backAndExitMenu;
    public InlineKeyboardMarkup addTokensMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Яндекс
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_YANDEX_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления VK
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_VK_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_VK_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления MyTarget
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_MYTARGET_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_MYTARGET_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Bitrix
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_BITRIX_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_BITRIX_STEP_1_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления AmoCRM
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_AMOCRM_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_AMOCRM_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Yclients
                                inlineKeyboardMaker.addButton(
                                        ButtonNameEnum.ADD_YCLIENTS_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_YCLIENTS_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
                        backAndExitMenu.backAndExitMenuButtons(chatId)
                )
        );
    }
}
