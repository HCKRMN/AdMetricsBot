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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsAnalyticsRequest {
    @Value("${bearer-authentication}")
    String bearerAuthentication;
    @Value("${user-token}")
    String userToken;

    @SneakyThrows
    public String yclientsAnalyticsRequest(String salonId){

        LocalDateTime now = LocalDateTime.now();

        //Выбираем отчет за вчерашний день
        String dateFrom = now
                .minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateTo = now
                .minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        HttpGet request = new HttpGet("https://api.yclients.com/api/v1/company/"+ salonId +"/analytics/overall/" +
                "?date_from=" + dateFrom + "&date_to=" + dateTo
                );

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
            log.error("Ошибка при получении количества состоявшихся записей̆ Yclients в салоне {} : {}", salonId, responseBody);
        }
        return responseBody;
    }
}