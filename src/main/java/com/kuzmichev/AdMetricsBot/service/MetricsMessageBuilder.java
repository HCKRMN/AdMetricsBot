package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.*;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixMessageBuilder;
import com.kuzmichev.AdMetricsBot.service.yandex.YandexMessageBuilder;
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

    public String getMessage(String chatId) {
        StringBuilder message = new StringBuilder();
        String lineBreak = "\n";
        List<Project> userProjects = projectRepository.findProjectsByChatId(chatId);

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String date = yesterday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        message.append(date).append(lineBreak).append(lineBreak);

        for (Project project : userProjects) {
            String projectId = project.getProjectId();
            String projectName = project.getProjectName();

            message
                    .append(projectName).append(lineBreak).append(lineBreak);

            if(yandexRepository.existsByProjectId(projectId)){
                message.append(yandexMessageBuilder.getMessage(projectId)).append(lineBreak);
            }
            if(bitrixRepository.existsByProjectId(projectId)){
                message.append(bitrixMessageBuilder.getMessage(projectId)).append(lineBreak);
            }
            message
                    .append(lineBreak)
                    .append(lineBreak);
        }
        return message.toString();
    }
}
