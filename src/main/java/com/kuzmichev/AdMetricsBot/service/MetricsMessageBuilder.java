package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.Project;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.model.YandexData;
import com.kuzmichev.AdMetricsBot.service.bitrix.BitrixMainRequest;
import com.kuzmichev.AdMetricsBot.service.yandex.YandexMainRequest;
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
    YandexMainRequest yandexMainRequest;

    public String getMessage(String chatId) {
        StringBuilder message = new StringBuilder();
        String lineBreak = "\n";
        String tab = "\t";
        List<Project> userProjects = projectRepository.findProjectsByChatId(chatId);

        for (Project project : userProjects) {
            YandexData yandexData = yandexMainRequest.yandexMainRequest(project.getProjectId());
            String projectId = project.getProjectId();
            String projectName = project.getProjectName();
            int impressions = yandexData.getImpressions();
            int clicks = yandexData.getClicks();
            double ctr = yandexData.getCtr();
            double avgCpc = yandexData.getAvgCpc();
            int conversions = yandexData.getConversions();
            double costPerConversion = yandexData.getCostPerConversion();
            double cost = yandexData.getCost();

            message
                    .append(projectName).append(lineBreak).append(lineBreak)

                    .append("Яндекс").append(lineBreak)
                    .append("<code>Показы:       </code>").append(impressions).append(lineBreak)
                    .append("<code>Клики:        </code>").append(clicks).append(lineBreak)
                    .append("<code>CTR:          </code>").append(ctr).append(lineBreak)
                    .append("<code>CPC:          </code>").append(avgCpc).append(lineBreak)
                    .append("<code>Конверсии:    </code>").append(conversions).append(lineBreak)
                    .append("<code>CPC:          </code>").append(costPerConversion).append(lineBreak)
                    .append("<code>Расход:       </code>").append(cost).append(lineBreak)
                    .append(bitrixMainRequest.bitrixMainRequest(projectId))
                    .append(lineBreak)
                    .append(lineBreak)
                    .append(lineBreak);
        }
        return message.toString();
    }
}
