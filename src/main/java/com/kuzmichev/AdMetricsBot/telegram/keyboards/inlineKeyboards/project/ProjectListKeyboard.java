package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.model.Project;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.InlineKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
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
public class ProjectListKeyboard implements InlineKeyboard {
    ProjectsDataTempKeeper projectsDataTempKeeper;
    ProjectRepository projectRepository;
    BackAndExitKeyboard backAndExitKeyboard;

    public InlineKeyboardMarkup getKeyboard(String chatId, String userState) {

        return InlineKeyboardMarkup.builder()
                .keyboard(getProjectListButtons(chatId))
                .keyboardRow(backAndExitKeyboard.getButtons(userState))
                .build();
    }

    public List<List<InlineKeyboardButton>> getProjectListButtons(String chatId) {
        String projectName;
        String projectCallback;

        int projectsPerPage = 5;
        int currentPage = projectsDataTempKeeper.getPage(chatId);

        List<Project> projects = projectRepository.findProjectsByChatId(chatId);
        int projectCount = projects.size();

        int startIndex = currentPage * projectsPerPage - projectsPerPage;
        int endIndex = Math.min(startIndex + projectsPerPage, projectCount);

        List<Project> projectsForPage = projects.subList(startIndex, endIndex);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Project project : projectsForPage) {
            projectName = project.getProjectName();
            projectCallback = "project_" + project.getProjectId();
            rows.add(
                    List.of(InlineKeyboardButton.builder()
                            .text(projectName)
                            .callbackData(projectCallback)
                            .build()));
        }
        rows.add(paginationButtons(currentPage, projectCount / projectsPerPage, projectCount));
        return rows;
    }

    private List<InlineKeyboardButton> paginationButtons(int currentPage, int totalPages, int projectCount) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        if (currentPage > 1) {
            buttons.add(
                    InlineKeyboardButton.builder()
                            .text(ButtonEnum.PROJECT_PREVIOUS_PAGE_BUTTON.getButtonName())
                            .callbackData(CallBackEnum.PROJECT_PAGE_CALLBACK.getCallBackName() + (currentPage - 1))
                            .build());
        }
        
        if (currentPage <= totalPages && projectCount != 5) {
            buttons.add(
                    InlineKeyboardButton.builder()
                            .text(ButtonEnum.PROJECT_NEXT_PAGE_BUTTON.getButtonName())
                            .callbackData(CallBackEnum.PROJECT_PAGE_CALLBACK.getCallBackName() + (currentPage + 1))
                            .build());
        }

        return buttons;
    }
}
