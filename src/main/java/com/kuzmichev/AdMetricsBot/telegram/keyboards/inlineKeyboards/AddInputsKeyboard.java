package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
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
public class AddInputsKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup addTokensMenu(String userState) {

        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Яндекс
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.ADD_YANDEX_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
//                        inlineKeyboardMaker.addRow(
//                                // Кнопка добавления VK
//                                inlineKeyboardMaker.addButton(
//                                        SettingsButtonEnum.ADD_VK_BUTTON.getButtonName(),
//                                        SettingsCallBackEnum.ADD_VK_CALLBACK.getCallBackName(),
//                                        null
//                                )
//                        ),
//                        inlineKeyboardMaker.addRow(
//                                // Кнопка добавления MyTarget
//                                inlineKeyboardMaker.addButton(
//                                        SettingsButtonEnum.ADD_MYTARGET_BUTTON.getButtonName(),
//                                        SettingsCallBackEnum.ADD_MYTARGET_CALLBACK.getCallBackName(),
//                                        null
//                                )
//                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Bitrix
                                inlineKeyboardMaker.addButton(
                                        SettingsButtonEnum.ADD_BITRIX_BUTTON.getButtonName(),
                                        SettingsCallBackEnum.ADD_BITRIX_STEP_1_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
//                        inlineKeyboardMaker.addRow(
//                                // Кнопка добавления AmoCRM
//                                inlineKeyboardMaker.addButton(
//                                        SettingsButtonEnum.ADD_AMOCRM_BUTTON.getButtonName(),
//                                        SettingsCallBackEnum.ADD_AMOCRM_CALLBACK.getCallBackName(),
//                                        null
//                                )
//                        ),
//                        inlineKeyboardMaker.addRow(
//                                // Кнопка добавления Yclients
//                                inlineKeyboardMaker.addButton(
//                                        SettingsButtonEnum.ADD_YCLIENTS_BUTTON.getButtonName(),
//                                        SettingsCallBackEnum.ADD_YCLIENTS_CALLBACK.getCallBackName(),
//                                        null
//                                )
//                        ),
                        backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}
