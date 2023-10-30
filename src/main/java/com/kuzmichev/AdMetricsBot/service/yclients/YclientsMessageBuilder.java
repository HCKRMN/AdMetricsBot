package com.kuzmichev.AdMetricsBot.service.yclients;

import com.kuzmichev.AdMetricsBot.model.YclientsData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsMessageBuilder {
    YclientsMainRequest yclientsMainRequest;

    public String getMessage(String projectId) {

        StringBuilder message = new StringBuilder();
        YclientsData yclientsData = yclientsMainRequest.yclientsMainRequest(projectId);
        int requestStatus = yclientsData.getRequestStatus();

        message.append("Yclients").append("\n");
        if (requestStatus == 200) {
            message
                    .append("<code>Новых записей:        </code>").append(yclientsData.getNewEntries()).append("\n")
                    .append("<code>Состоявшиеся записи:  </code>").append(yclientsData.getSuccessEntries()).append("\n")
                    .append("<code>Проваленные записи:   </code>").append(yclientsData.getCanceledEntries()).append("\n");
        } else if (requestStatus == -1) {
            String errorMessage = yclientsData.getRequestErrorMessage();
            message
                    .append(errorMessage).append("\n");
            log.error("У пользователя {} возникла ошибка при получении данных от Yclients: {}", yclientsData.getChatId(), errorMessage);
        }
        return message.toString();
    }
}
