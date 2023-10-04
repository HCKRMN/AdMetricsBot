package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.Project;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixMainRequest;
import com.kuzmichev.AdMetricsBot.service.yandex.YandexTestMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MetricsMessageBuilder {
    ProjectRepository projectRepository;
    BitrixMainRequest bitrixMainRequest;
    YandexTestMessage yandexTestMessage;

    public String getMessage(String chatId) {
        StringBuilder message = new StringBuilder();
        String lineBreak = "\n";
        List<Project> userProjects = projectRepository.findProjectsByChatId(chatId);

        for (Project project : userProjects) {
            message
                    .append(project.getProjectName())
                    .append(lineBreak)
                    .append(bitrixMainRequest.bitrixMainRequest(project.getProjectId()))
                    .append(lineBreak)
                    .append(yandexTestMessage.getYandexTestMessage(project.getProjectId()))
                    .append(lineBreak)
                    .append(lineBreak);
        }
        return message.toString();
    }
}
