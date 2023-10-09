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
public class AddInputsKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup addTokensMenu(String userState) {

        return inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Яндекс
                                inlineKeyboardMaker.addButton(
                                        ButtonEnum.ADD_YANDEX_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
//                        inlineKeyboardMaker.addRow(
//                                // Кнопка добавления VK
//                                inlineKeyboardMaker.addButton(
//                                        ButtonEnum.ADD_VK_BUTTON.getButtonName(),
//                                        CallBackEnum.ADD_VK_CALLBACK.getCallBackName(),
//                                        null
//                                )
//                        ),
//                        inlineKeyboardMaker.addRow(
//                                // Кнопка добавления MyTarget
//                                inlineKeyboardMaker.addButton(
//                                        ButtonEnum.ADD_MYTARGET_BUTTON.getButtonName(),
//                                        CallBackEnum.ADD_MYTARGET_CALLBACK.getCallBackName(),
//                                        null
//                                )
//                        ),
                        inlineKeyboardMaker.addRow(
                                // Кнопка добавления Bitrix
                                inlineKeyboardMaker.addButton(
                                        ButtonEnum.ADD_BITRIX_BUTTON.getButtonName(),
                                        CallBackEnum.ADD_BITRIX_STEP_1_CALLBACK.getCallBackName(),
                                        null
                                )
                        ),
//                        inlineKeyboardMaker.addRow(
//                                // Кнопка добавления AmoCRM
//                                inlineKeyboardMaker.addButton(
//                                        ButtonEnum.ADD_AMOCRM_BUTTON.getButtonName(),
//                                        CallBackEnum.ADD_AMOCRM_CALLBACK.getCallBackName(),
//                                        null
//                                )
//                        ),
//                        inlineKeyboardMaker.addRow(
//                                // Кнопка добавления Yclients
//                                inlineKeyboardMaker.addButton(
//                                        ButtonEnum.ADD_YCLIENTS_BUTTON.getButtonName(),
//                                        CallBackEnum.ADD_YCLIENTS_CALLBACK.getCallBackName(),
//                                        null
//                                )
//                        ),
                        backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}
