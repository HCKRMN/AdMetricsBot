package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
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

        int projectsCount = userRepository.getProjectsCountByChatId(chatId) + 1;

        // Генерируем UUID и переводим в текст
        String projectId = UUID.randomUUID().toString();

        Project project = new Project();
        project.setProjectName(messageText);
        project.setChatId(chatId);
        project.setProjectId(projectId);
        project.setProjectNumber(projectsCount);
        projectRepository.save(project);

        Optional<User> userOptional = userRepository.findByChatId(chatId);
        User user = userOptional.orElseGet(User::new);
        user.setChatId(chatId);
        user.setProjectsCount(projectsCount);
        userRepository.save(user);

        TempData tempData = new TempData();
        tempData.setLastProjectId(projectId);
        tempData.setChatId(chatId);
        tempDataRepository.save(tempData);
    }
}

















