package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
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
public class BackAndExitMenu {
    UserRepository userRepository;
    InlineKeyboardMaker inlineKeyboardMaker;
    public List<InlineKeyboardButton> backAndExitMenuButtons(String chatId) {

        String userState = userRepository.getUserStateByChatId(chatId);
        System.out.println(userState);

        CallBackEnum backButtonCallBackEnum;

        switch (UserStateEnum.valueOf(userState)){
            case    SETTINGS_EDIT_STATE,
                    SETTINGS_EDIT_TIMER_STATE,
                    SETTINGS_PROJECTS_STATE,
                    SETTINGS_EDIT_TIMEZONE_STATE
                    -> backButtonCallBackEnum = CallBackEnum.SETTINGS_BACK_CALLBACK;

            case SETTINGS_PROJECT_CREATE_STATE,
                    SETTINGS_PROJECT_ADD_TOKENS_STATE
                    -> backButtonCallBackEnum = CallBackEnum.PROJECTS_CALLBACK;

            default -> backButtonCallBackEnum = CallBackEnum.SETTINGS_EXIT_CALLBACK;

        }

        return inlineKeyboardMaker.addRow(
                // Кнопка назад
                inlineKeyboardMaker.addButton(
                        ButtonNameEnum.SETTINGS_BACK_BUTTON.getButtonName(),
                        backButtonCallBackEnum.getCallBackName(),
                        null
                ),
                // Кнопка выход
                inlineKeyboardMaker.addButton(
                        ButtonNameEnum.SETTINGS_EXIT_BUTTON.getButtonName(),
                        CallBackEnum.SETTINGS_EXIT_CALLBACK.getCallBackName(),
                        null
                )

        );
    }
    // Это универсальное меню с двумя кнопками: Назад и Выход
    public InlineKeyboardMarkup backAndExitMenu(String chatId) {
        return inlineKeyboardMaker.addMarkup(
                inlineKeyboardMaker.addRows(
                        backAndExitMenuButtons(chatId)
                )
        );
    }
}
