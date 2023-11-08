package com.kuzmichev.AdMetricsBot.service.yclients;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsTestConnection {
    @Value("${bearer-authentication}")
    String bearerAuthentication;
    @Value("${user-token}")
    String userToken;
    @Value("${yclientsRequestRecordsURL}")
    String yclientsRequestURL;

    @SneakyThrows
    public String yclientsTestConnection(String salonId){

        //Фильтр записей по дате создания, выбираем вчерашний день
        String date = LocalDateTime.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        HttpGet request = new HttpGet(yclientsRequestURL + salonId +
                "?c_start_date=" + date + "&c_end_date=" + date +
                "&start_date=" + date + "&end_date=" + date);

        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + bearerAuthentication + ", User " + userToken);
        request.addHeader("Accept", "application/vnd.yclients.v2+json");

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        // Получаем ответ
        HttpEntity entityResponse = response.getEntity();
        String responseBody = EntityUtils.toString(entityResponse);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            JSONObject jsonResponse = new JSONObject(responseBody);
            log.error("Ошибка при выполнении тестового запроса Yclients в салоне {} : {}, {}", salonId, statusCode, responseBody);
            return jsonResponse.getJSONObject("meta").getString("message");
        }
        return "200";
    }
}