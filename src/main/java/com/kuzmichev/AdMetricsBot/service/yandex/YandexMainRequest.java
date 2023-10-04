package com.kuzmichev.AdMetricsBot.service.yandex;

import com.kuzmichev.AdMetricsBot.model.Yandex;
import com.kuzmichev.AdMetricsBot.model.YandexData;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YandexMainRequest {
    YandexRepository yandexRepository;

    public YandexData yandexMainRequest(String projectId){
        Yandex yandex = yandexRepository.findByProjectId(projectId);
        String bearer = yandex.getYandexToken();

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api.direct.yandex.com/json/v5/reports");
        request.addHeader("Authorization", "Bearer " + bearer);
        request.addHeader("Accept-Language", "en");
        request.addHeader("method", "post");
        request.addHeader("content-type", "application/json; charset=utf-8");
        request.addHeader("returnMoneyInMicros", "false");
        request.addHeader("skipReportHeader", "true");
        StringEntity entity;
        String responseBody;
        try {
            entity = new StringEntity("""
                        {
                            "params": {
                              "SelectionCriteria": {
                              },
                              "FieldNames": [ "Date", "Impressions", "Ctr", "Clicks", "AvgCpc", "Conversions", "CostPerConversion", "Cost" ],\s
                              "OrderBy": [{
                                "Field": "Date"
                              }],
                              "ReportName": "Actual Data",
                              "ReportType": "ACCOUNT_PERFORMANCE_REPORT",
                              "DateRangeType": "YESTERDAY",
                              "Format": "TSV",
                              "IncludeVAT": "YES",
                              "IncludeDiscount": "YES"
                            }
                          }""");

            // Указываем тело запроса и отправляем
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);

            // Получаем ответ
            HttpEntity entityResponse = response.getEntity();
            responseBody = EntityUtils.toString(entityResponse);
            int statusCode = response.getStatusLine().getStatusCode();

            YandexData yandexData = new YandexData();
            yandexData.setProjectId(projectId);

            // Проверяем есть-ли строка с данными
            if (responseBody.contains("Total rows: 1")) {
                // Разбиваем текст на строки
                String[] lines = responseBody.split("\\n");

                // Выбираем строку с данными
                String line = lines[1];

                // Разбиваем строку на значения по символу табуляции
                String[] values = line.split("\t");

                yandexData.setImpressions(Integer.parseInt(values[1]));
                yandexData.setCtr(Double.parseDouble(values[2]));
                yandexData.setClicks(Integer.parseInt(values[3]));
                yandexData.setAvgCpc(Double.parseDouble(values[4]));
                yandexData.setConversions(Integer.parseInt(values[5]));
                yandexData.setCostPerConversion(Double.parseDouble(values[6]));
                yandexData.setCost(Double.parseDouble(values[7]));
            }

            if (statusCode == 200) {
                return yandexData;
            } else {
                log.error("Ответ не содержал данных");
                log.info("Request: {}", request);
                log.info("Request Headers: {}", Arrays.toString(request.getAllHeaders()));
                log.info("Request Body: {}", EntityUtils.toString(entity));
                log.info("Response: {}", response);
                log.info("Response Status Line: {}", response.getStatusLine());
                log.info("Response Body: {}", responseBody);
                return null;
            }
        } catch (IOException e) {
            log.error("У пользователя " + yandex.getChatId() + " произошла ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}