package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectManager {
    ProjectRepository projectRepository;
    ProjectsDataTempKeeper projectsDataTempKeeper;

    public void projectCreate(String chatId, String projectName) {

        long nextProjectNumber = projectRepository.findMaxProjectNumberByChatId(chatId) + 1;

        // Генерируем UUID и переводим в текст
        String projectId = UUID.randomUUID().toString();

        Project project = new Project();
        project.setProjectName(projectName);
        project.setChatId(chatId);
        project.setProjectId(projectId);
        project.setProjectNumber(nextProjectNumber);
        projectRepository.save(project);

        projectsDataTempKeeper.setLastProjectId(chatId, projectId);
    }
}

















