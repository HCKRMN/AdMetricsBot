package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalCallbackEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectsKeyboard {
    UserRepository userRepository;
    InlineKeyboardMaker inlineKeyboardMaker;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup projectsMenu(String chatId, String userState) {


        // Кнопка получения списка проектов
        List<InlineKeyboardButton> projectsButton = inlineKeyboardMaker.addRow(
                inlineKeyboardMaker.addButton(
                        SettingsButtonEnum.PROJECTS_GET_LIST_BUTTON.getButtonName(),
                        SettingsCallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName(),
                        null
                )
        );
        if (userRepository.getProjectsCountByChatId(chatId) == 0) {
            projectsButton = null;
        }
//        return inlineKeyboardMarkup;
        return inlineKeyboardMaker.addMarkup(
                    inlineKeyboardMaker.addRow(
                            inlineKeyboardMaker.addButton(
                                    UniversalButtonEnum.PROJECT_CREATE_BUTTON.getButtonName(),
                                    UniversalCallbackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName(),
                                    null
                            )

                )
                        ,projectsButton
                        ,backAndExitKeyboard.backAndExitMenuButtons(userState)
        );
    }
}
