package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.kuzmichev.AdMetricsBot.model.Bitrix;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CrmTypeRequest {
    BitrixApiService bitrixApiService;
    public int crmTypeRequest(String accessToken, String domain, String chatId) {

        String url = String.format("https://%s/rest/crm.settings.mode.get?auth=%s",
                domain, accessToken);

            JSONObject json = bitrixApiService.makeBitrixApiRequest(url, chatId);
            if (json != null) {
                return json.getInt("result");
            } else {
                log.error("User " + chatId + ". Ошибка при получении типа срм bitrix: json == null");
                return 0;
            }
    }
}
