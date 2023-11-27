package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.InlineKeyboard;
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
public class ProjectsKeyboard implements InlineKeyboard {
    BackAndExitKeyboard backAndExitKeyboard;
    ProjectRepository projectRepository;

    public InlineKeyboardMarkup getKeyboard(String chatId, String userState) {
        // Кнопка получения списка проектов
        List<InlineKeyboardButton> projectsButton = List.of(InlineKeyboardButton.builder()
                .text(ButtonEnum.PROJECTS_GET_LIST_BUTTON.getButtonName())
                .callbackData(CallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName())
                .build());

        // Если нет проектов
        if (projectRepository.findProjectsCountByChatId(chatId) == 0) {
            projectsButton = List.of();
        }

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.PROJECT_CREATE_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK.getCallBackName())
                        .build()))
                .keyboardRow(projectsButton)
                .keyboardRow(backAndExitKeyboard.getButtons(userState))
                .build();
    }
}
