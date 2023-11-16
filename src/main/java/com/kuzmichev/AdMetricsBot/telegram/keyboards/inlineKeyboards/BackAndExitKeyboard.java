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
public class BackAndExitKeyboard implements InlineKeyboard {
    public List<InlineKeyboardButton> getButtons(String userState) {
            // Возвращаем пустой лист так как при регистрации пользователь не может выйти
        if (userState.contains(StateEnum.REGISTRATION.getStateName())) {
            return List.of();
        }

        String backButtonSettingsCallBackEnum;
        switch (StateEnum.valueOf(userState)) {
            case SETTINGS_STATE,
                    SETTINGS_EDIT_TIMER_STATE,
                    SETTINGS_PROJECTS_STATE,
                    SETTINGS_EDIT_TIMEZONE_STATE -> backButtonSettingsCallBackEnum = CallBackEnum.SETTINGS_BACK_CALLBACK.getCallBackName();

            case SETTINGS_PROJECT_CREATE_STATE,
                    SETTINGS_ADD_INPUTS_STATE -> backButtonSettingsCallBackEnum = CallBackEnum.PROJECTS_CALLBACK.getCallBackName();

            default -> backButtonSettingsCallBackEnum = CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName();
        }

        return List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.SETTINGS_BACK_BUTTON.getButtonName())
                        .callbackData(backButtonSettingsCallBackEnum)
                        .build(),
                InlineKeyboardButton.builder()
                        .text(ButtonEnum.SETTINGS_EXIT_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName())
                        .build());
    }

    // Универсальное меню с двумя кнопками: Назад и Выход
    public InlineKeyboardMarkup getKeyboard(String userState, String chatId) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(getButtons(userState)).build();
    }
}
