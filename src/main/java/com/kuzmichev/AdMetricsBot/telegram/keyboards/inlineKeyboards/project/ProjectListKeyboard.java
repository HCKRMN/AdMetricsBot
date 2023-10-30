package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.Project;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
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
public class ProjectListKeyboard {
    InlineKeyboardMaker inlineKeyboardMaker;
    UserRepository userRepository;
    ProjectRepository projectRepository;
    BackAndExitKeyboard backAndExitKeyboard;
    public InlineKeyboardMarkup projectListMenu(String chatId, String userState) {
        String projectName;
        String projectCallback;

        int projectsPerPage = 5;
        int currentPage = userRepository.getProjectsPageByChatId(chatId);

        if (currentPage == 0) {
            currentPage = 1;
        }

        List<Project> projects = projectRepository.findProjectsByChatId(chatId);

        int startIndex = currentPage * projectsPerPage - projectsPerPage;
        int endIndex = Math.min(startIndex + projectsPerPage, projects.size());

        List<Project> projectsForPage = projects.subList(startIndex, endIndex);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Project project : projectsForPage) {
            projectName = project.getProjectName();
            projectCallback = "project_" + project.getProjectId();
            rows.add(
                    inlineKeyboardMaker.addRow(
                            inlineKeyboardMaker.addButton(
                                    projectName,
                                    projectCallback,
                                    null
                            )
                    )
            );
        }
        rows.add(paginationButtons(currentPage, projects.size() / projectsPerPage));
        rows.add(backAndExitKeyboard.backAndExitMenuButtons(userState));

        return inlineKeyboardMaker.addMarkup(rows);
    }

    private List<InlineKeyboardButton> paginationButtons(int currentPage, int totalPages) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        if (currentPage > 1) {
            buttons.add(
                    inlineKeyboardMaker.addButton(
                            ButtonEnum.PROJECT_PREVIOUS_PAGE_BUTTON.getButtonName(),
                            CallBackEnum.PROJECT_PAGE_CALLBACK.getCallBackName() + (currentPage - 1),
                            null
                    )
            );
        }

        if (currentPage <= totalPages ) {
            buttons.add(
                    inlineKeyboardMaker.addButton(
                            ButtonEnum.PROJECT_NEXT_PAGE_BUTTON.getButtonName(),
                            CallBackEnum.PROJECT_PAGE_CALLBACK.getCallBackName() + (currentPage + 1),
                            null
                    )
            );
        }
        return buttons;
    }
}
