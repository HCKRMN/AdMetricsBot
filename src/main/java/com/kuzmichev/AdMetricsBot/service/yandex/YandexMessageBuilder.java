package com.kuzmichev.AdMetricsBot.service.yandex;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.model.YandexData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YandexMessageBuilder {
    YandexMainRequest yandexMainRequest;

    public String getMessage(String projectId) {
        StringBuilder message = new StringBuilder();
        YandexData yandexData = yandexMainRequest.yandexMainRequest(projectId);

        int impressions = yandexData.getImpressions();
        int clicks = yandexData.getClicks();
        double ctr = yandexData.getCtr();
        double avgCpc = yandexData.getAvgCpc();
        int conversions = yandexData.getConversions();
        double costPerConversion = yandexData.getCostPerConversion();
        double cost = yandexData.getCost();
        int requestStatus = yandexData.getRequestStatus();

        message.append("Яндекс").append("\n");
        if (requestStatus == 200) {
            message
                    .append("<code>Показы:             </code>").append(impressions).append("\n")
                    .append("<code>Клики:              </code>").append(clicks).append("\n")
                    .append("<code>CTR:                </code>").append(ctr).append("\n")
                    .append("<code>CPC:                </code>").append(avgCpc).append("\n")
                    .append("<code>Конверсии:          </code>").append(conversions).append("\n")
                    .append("<code>CPA:                </code>").append(costPerConversion).append("\n")
                    .append("<code>Расход:             </code>").append(cost).append("\n");
        } else if(requestStatus == 513) {
            message.append(MessageEnum.YANDEX_ERROR_513_MESSAGE.getMessage()).append("\n");

        } else if(requestStatus == 53) {
            message.append(MessageEnum.YANDEX_ERROR_53_MESSAGE.getMessage()).append("\n");

        } else {
            message.append(MessageEnum.YANDEX_ERROR_UNKNOWN_MESSAGE.getMessage()).append("\n");
        }

        return message.toString();
    }
}
