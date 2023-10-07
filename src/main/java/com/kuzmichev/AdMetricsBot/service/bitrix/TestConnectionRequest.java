package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TestConnectionRequest {
    public int testConnectionRequest(Bitrix bitrix, String chatId) throws Exception {

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
        } else if(responseCode == 401){
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Разбираем JSON-ответ
                JSONObject jsonResponse = new JSONObject(response.toString());
                String errorDescription = jsonResponse.getString("error_description");

                if (errorDescription.contains("commercial plans")) {
                    responseCode = 402;
                }
            }
        } else {
            log.warn("Пользователь " + chatId + ". Тестовый запрос к bitrix не прошел, responseCode: " + responseCode);
        }
        return responseCode;
    }
}
