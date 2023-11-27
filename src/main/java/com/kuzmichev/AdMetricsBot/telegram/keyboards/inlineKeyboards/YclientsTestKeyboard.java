package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
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
public class YclientsTestKeyboard implements InlineKeyboard {

    public InlineKeyboardMarkup getKeyboard(String chatId, String userState) {
        String callBack = CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName();
        if (userState.contains(StateEnum.REGISTRATION.getStateName())) {
            callBack = CallBackEnum.EDIT_TIMER_CALLBACK.getCallBackName();
        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.TEST_INPUTS_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.TEST_YCLIENTS_CALLBACK.getCallBackName())
                        .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.SETTINGS_ADD_ACCOUNTS_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName())
                        .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.CONTINUE_BUTTON.getButtonName())
                        .callbackData(callBack)
                        .build()))
                .build();
    }
}