package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TestConnectionRequest {
    public int testConnectionRequest(Bitrix bitrix) throws Exception {

        String domain = bitrix.getBitrixDomain();
        String accessToken = bitrix.getAccessToken();

        String url = String.format("https://%s/rest/server.time?&auth=%s", domain, accessToken);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();

        if (responseCode == 200) {
            log.info("Пользователь " + bitrix.getChatId() + ". Тестовый запрос к bitrix успешен, responseCode: " + responseCode);
        } else {
            log.warn("Пользователь " + bitrix.getChatId() + ". Тестовый запрос к bitrix не прошел, responseCode: " + responseCode);
        }
        return responseCode;
    }
}
