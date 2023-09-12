package com.kuzmichev.AdMetricsBot.telegram.InlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Menu.BackAndExitCallbackHandler;
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
public class ProjectsMenu {
    UserRepository userRepository;
    InlineKeyboardMaker inlineKeyboardMaker;
    BackAndExitMenu backAndExitMenu;
    public InlineKeyboardMarkup projectsMenu(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);

        // Кнопка добавления проекта
        List<InlineKeyboardButton> createProjectButton = inlineKeyboardMaker.addRow(
                inlineKeyboardMaker.addButton(
                        ButtonNameEnum.PROJECT_CREATE_BUTTON.getButtonName(),
                        CallBackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName(),
                        null
                )
        );

        // Кнопка получения списка проектов
        List<InlineKeyboardButton> projectsButton = inlineKeyboardMaker.addRow(
                inlineKeyboardMaker.addButton(
                        ButtonNameEnum.PROJECTS_GET_LIST_BUTTON.getButtonName(),
                        CallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName(),
                        null
                )
        );
        rows.add(createProjectButton);
        if (userRepository.getProjectsCountByChatId(chatId) != 0) {
            rows.add(projectsButton);
        }
        rows.add(backAndExitMenu.backAndExitMenuButtons(chatId));
        return inlineKeyboardMarkup;
    }
}
