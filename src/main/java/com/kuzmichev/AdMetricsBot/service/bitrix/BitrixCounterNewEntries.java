package com.kuzmichev.AdMetricsBot.service.bitrix;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixCounterNewEntries {
    BitrixApiService bitrixApiService;
    CrmTypeRequest crmTypeRequest;

    public int countRecordsRequest(String accessToken, String domain, String chatId) {

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String dateFrom = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00"));
        String dateTo = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'23:59:59"));

        String crmType = crmTypeRequest.crmTypeRequest(accessToken, domain, chatId) == 1 ? "crm.lead.list" : "crm.deal.list";
        String url = String.format("https://%s/rest/%s?filter[>DATE_CREATE]=%s&filter[<DATE_CREATE]=%s&auth=%s",
                domain, crmType, dateFrom, dateTo, accessToken);

        JSONObject json = bitrixApiService.makeBitrixApiRequest(url, chatId);
        if (json != null) {
            return json.getInt("total");
        } else {
            log.error("User " + chatId + ". Ошибка при получении типа срм bitrix: json == null. url: " + url);
            return -1;
        }

    }
}

