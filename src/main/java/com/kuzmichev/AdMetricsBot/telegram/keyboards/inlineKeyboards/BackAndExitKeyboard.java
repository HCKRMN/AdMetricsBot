package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsStateEnum;
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
            switch (SettingsStateEnum.valueOf(userState)) {
                case SETTINGS_EDIT_STATE,
                        SETTINGS_EDIT_TIMER_STATE,
                        SETTINGS_PROJECTS_STATE,
                        SETTINGS_EDIT_TIMEZONE_STATE -> backButtonSettingsCallBackEnum = SettingsCallBackEnum.SETTINGS_BACK_CALLBACK.getCallBackName();

                case SETTINGS_PROJECT_CREATE_STATE,
                        SETTINGS_PROJECT_ADD_TOKENS_STATE -> backButtonSettingsCallBackEnum = SettingsCallBackEnum.PROJECTS_CALLBACK.getCallBackName();

                default -> backButtonSettingsCallBackEnum = SettingsCallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName();
            }
            return inlineKeyboardMaker.addRow(
                    // Кнопка назад
                    inlineKeyboardMaker.addButton(
                            SettingsButtonEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                            backButtonSettingsCallBackEnum,
                            null
                    ),
                    // Кнопка выход
                    inlineKeyboardMaker.addButton(
                            SettingsButtonEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                            SettingsCallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                            null
                    )
            );
        } else {
//            Возвращаем здесь null потому что далее он будет обработан в методе отправке сообщений
            return null;
        }

    }
    // Это универсальное меню с двумя кнопками: Назад и Выход
    public InlineKeyboardMarkup backAndExitMenu(String userState) {
        return inlineKeyboardMaker.addMarkup(
                        backAndExitMenuButtons(userState)
        );
    }
}
