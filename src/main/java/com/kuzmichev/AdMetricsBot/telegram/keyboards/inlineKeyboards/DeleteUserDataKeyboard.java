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
public class DeleteUserDataKeyboard {
    public InlineKeyboardMarkup deleteUserDataMenu() {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.DELETE_USER_DATA_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.DELETE_USER_STEP_2_CALLBACK.getCallBackName())
                        .build()))
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.NOT_DELETE_USER_DATA_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.NOT_DELETE_USER_CALLBACK.getCallBackName())
                        .build()))
                .build();
    }
}
