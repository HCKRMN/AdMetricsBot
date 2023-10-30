package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.*;
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
    TempDataRepository tempDataRepository;
    UserRepository userRepository;

    public void projectCreate(String chatId, String messageText) {

        User user = userRepository.findByChatId(chatId).orElseGet(User::new);

        long nextProjectNumber = user.getLastProjectNumber() + 1;
        long projectCount = user.getProjectsCount() + 1;

        // Генерируем UUID и переводим в текст
        String projectId = UUID.randomUUID().toString();

        Project project = new Project();
        project.setProjectName(messageText);
        project.setChatId(chatId);
        project.setProjectId(projectId);
        project.setProjectNumber(nextProjectNumber);
        projectRepository.save(project);

        user.setChatId(chatId);
        user.setProjectsCount(projectCount);
        user.setLastProjectNumber(nextProjectNumber);
        userRepository.save(user);

        TempData tempData = new TempData();
        tempData.setLastProjectId(projectId);
        tempData.setChatId(chatId);
        tempDataRepository.save(tempData);
    }
}

















