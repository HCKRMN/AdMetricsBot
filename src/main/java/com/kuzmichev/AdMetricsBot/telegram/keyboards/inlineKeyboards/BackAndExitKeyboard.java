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
public class BackAndExitKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    public List<InlineKeyboardButton> backAndExitMenuButtons(String userState) {

        if(userState.contains("SETTINGS")) {
            String backButtonSettingsCallBackEnum;
            switch (StateEnum.valueOf(userState)) {
                case SETTINGS_EDIT_STATE,
                        EDIT_TIMER_STATE,
                        SETTINGS_PROJECTS_STATE,
                        SETTINGS_EDIT_TIMEZONE_STATE -> backButtonSettingsCallBackEnum = CallBackEnum.SETTINGS_BACK_CALLBACK.getCallBackName();

                case SETTINGS_PROJECT_CREATE_STATE,
                        SETTINGS_PROJECT_ADD_TOKENS_STATE -> backButtonSettingsCallBackEnum = CallBackEnum.PROJECTS_CALLBACK.getCallBackName();

                default -> backButtonSettingsCallBackEnum = CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName();
            }
            return inlineKeyboardMaker.addRow(
                    // Кнопка назад
                    inlineKeyboardMaker.addButton(
                            ButtonEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                            backButtonSettingsCallBackEnum,
                            null
                    ),
                    // Кнопка выход
                    inlineKeyboardMaker.addButton(
                            ButtonEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                            CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                            null
                    )
            );
        } else {
//          Возвращаем null потому что в этом случае кнопки нам ненужны
            return null;
        }

    }
    // Универсальное меню с двумя кнопками: Назад и Выход
    public InlineKeyboardMarkup backAndExitMenu(String userState) {
        return inlineKeyboardMaker.addMarkup(
                        backAndExitMenuButtons(userState)
        );
    }
}
