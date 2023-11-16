package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixMessageBuilder;
import com.kuzmichev.AdMetricsBot.service.yandex.YandexMessageBuilder;
import com.kuzmichev.AdMetricsBot.service.yclients.YclientsMessageBuilder;
import com.kuzmichev.AdMetricsBot.telegram.utils.ExistInputsChecker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MetricsMessageBuilder {
    ProjectRepository projectRepository;
    BitrixMessageBuilder bitrixMessageBuilder;
    YandexMessageBuilder yandexMessageBuilder;
    YandexRepository yandexRepository;
    BitrixRepository bitrixRepository;
    YclientsRepository yclientsRepository;
    YclientsMessageBuilder yclientsMessageBuilder;
    ExistInputsChecker existInputsChecker;

    public String getAllProjectsMessage(String chatId) {
        StringBuilder message = new StringBuilder();
        String lineBreak = "\n";
        List<Project> userProjects = projectRepository.findProjectsByChatId(chatId);

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String date = yesterday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        message.append(date).append(lineBreak);


        for (Project project : userProjects) {
            if(existInputsChecker.isExist(project.getProjectId())){
                String projectId = project.getProjectId();
                String projectName = project.getProjectName();

                message.append(projectName).append(lineBreak).append(lineBreak);

                message.append(yandexMessageBuilder.getMessage(projectId)).append(lineBreak);
                message.append(bitrixMessageBuilder.getMessage(projectId)).append(lineBreak);
                message.append(yclientsMessageBuilder.getMessage(projectId)).append(lineBreak);

                message
                        .append(lineBreak)
                        .append(lineBreak);
            }
        }
        return message.toString();
    }

    public String getOneProjectMessage(String projectId) {
        StringBuilder message = new StringBuilder();
        String lineBreak = "\n";
        Project project = projectRepository.findProjectByProjectId(projectId);
        String projectName = project.getProjectName();

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String date = yesterday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        message.append(date).append(lineBreak);
        message.append(projectName).append(lineBreak).append(lineBreak);

        message.append(yandexMessageBuilder.getMessage(projectId)).append(lineBreak);
        message.append(bitrixMessageBuilder.getMessage(projectId)).append(lineBreak);
        message.append(yclientsMessageBuilder.getMessage(projectId)).append(lineBreak);

        return message.toString();
    }
}
