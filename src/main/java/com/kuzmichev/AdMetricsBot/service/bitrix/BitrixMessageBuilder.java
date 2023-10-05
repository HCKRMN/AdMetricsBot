package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.kuzmichev.AdMetricsBot.model.BitrixData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixMessageBuilder {
    BitrixMainRequest bitrixMainRequest;

    public String getMessage(String projectId) {
        StringBuilder message = new StringBuilder();
        BitrixData bitrixData = bitrixMainRequest.bitrixMainRequest(projectId);

        int newLeads = bitrixData.getNewLeads();
        int successDeals = bitrixData.getSuccessDeals();
        int failedDeals = bitrixData.getFailedDeals();

        message.append("Битрикс").append("\n");
        if (bitrixData.getRequestStatus() == 1) {
            message
                    .append("<code>Новых лидов:        </code>").append(newLeads).append("\n")
                    .append("<code>Успешные сделки:    </code>").append(successDeals).append("\n")
                    .append("<code>Проваленные сделки: </code>").append(failedDeals).append("\n");
        } else {
            message
                    .append("Ошибка получения данных от битрикса").append("\n");
        }

        return message.toString();
    }
}
