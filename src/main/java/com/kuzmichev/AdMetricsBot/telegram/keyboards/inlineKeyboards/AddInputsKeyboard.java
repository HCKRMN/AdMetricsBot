package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddInputsKeyboard implements InlineKeyboard {
    BackAndExitKeyboard backAndExitKeyboard;

    public InlineKeyboardMarkup getKeyboard(String userState, String chatId) {

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.ADD_YANDEX_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.ADD_YANDEX_CALLBACK.getCallBackName())
                        .build()))
//                .keyboardRow(List.of(InlineKeyboardButton.builder()
//                        .text(ButtonEnum.ADD_VK_BUTTON.getButtonName())
//                        .callbackData(CallBackEnum.ADD_VK_CALLBACK.getCallBackName())
//                        .build()))
//                .keyboardRow(List.of(InlineKeyboardButton.builder()
//                        .text(ButtonEnum.ADD_MYTARGET_BUTTON.getButtonName())
//                        .callbackData(CallBackEnum.ADD_MYTARGET_CALLBACK.getCallBackName())
//                        .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.ADD_BITRIX_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.ADD_BITRIX_STEP_1_CALLBACK.getCallBackName())
                        .build()))
//                .keyboardRow(List.of(InlineKeyboardButton.builder()
//                        .text(ButtonEnum.ADD_AMOCRM_BUTTON.getButtonName())
//                        .callbackData(CallBackEnum.ADD_AMOCRM_CALLBACK.getCallBackName())
//                        .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.ADD_YCLIENTS_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.ADD_YCLIENTS_CALLBACK.getCallBackName())
                        .build()))

                .keyboardRow(backAndExitKeyboard.getButtons(userState))
                .build();
    }
}
