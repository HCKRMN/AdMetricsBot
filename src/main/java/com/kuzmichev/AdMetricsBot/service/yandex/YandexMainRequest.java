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
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class YandexMainRequest {
    private final YandexRepository yandexRepository;
    protected HttpClient httpClient = HttpClientBuilder.create().build();

    public YandexData yandexMainRequest(String projectId){
        Yandex yandex = yandexRepository.findByProjectId(projectId);
        String chatId = yandex.getChatId();
        String bearer = yandex.getYandexToken();

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

            System.out.println(responseBody);

            YandexData yandexData = new YandexData();
            yandexData.setProjectId(projectId);

            if (statusCode == 200) {
                yandexData.setRequestStatus(200);
                // Проверяем есть-ли строка с данными, если есть - заполняем, нет - оставляем дефолтные значения
                // Если в ответе яндекса нет строки с результатами, но статус 200 это значит что расходов и статистики за этот период нет
                 if (responseBody.contains("Total rows: 1")) {
                    // Разбиваем текст на строки
                    String[] lines = responseBody.split("\\n");

                    // Выбираем строку с данными
                    String line = lines[1];

                    // Разбиваем строку на значения по символу табуляции
                    String[] values = line.split("\t");

                    // В некоторых значениях яндекс вместо "0" возвращает "--",
                    // поэтому обработаем эти значения
                    for (int i = 0; i < values.length; i++) {
                       if (values[i].contains("--")) {
                           values[i] = "0";
                       }
                    }

                    yandexData.setImpressions(Integer.parseInt(values[1]));
                    yandexData.setCtr(Double.parseDouble(values[2]));
                    yandexData.setClicks(Integer.parseInt(values[3]));
                    yandexData.setAvgCpc(Double.parseDouble(values[4]));
                    yandexData.setConversions(Integer.parseInt(values[5]));
                    yandexData.setCostPerConversion(Double.parseDouble(values[6]));
                    yandexData.setCost(Double.parseDouble(values[7]));
                }
            } else if(statusCode == 400){
                try {
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    if (jsonResponse.has("error")) {
                        JSONObject errorObject = jsonResponse.getJSONObject("error");
                        if (errorObject.has("error_code")) {
                            String errorCode = errorObject.getString("error_code");
                            yandexData.setRequestStatus(Integer.parseInt(errorCode));
                        }
                    }
                } catch (JSONException e) {
                    log.error("Пользователь {}. Не удалось извлечь значение 'error_code' из JSON-ответа от Яндекса: {}", chatId, e.getMessage());
                }

            } else {
                yandexData.setRequestStatus(statusCode);
                log.error("Пользователь {}. Ответ не содержал данных", chatId);
                log.info("Request: {}", request);
                log.info("Request Headers: {}", Arrays.toString(request.getAllHeaders()));
                log.info("Request Body: {}", EntityUtils.toString(entity));
                log.info("Response: {}", response);
                log.info("Response Status Line: {}", response.getStatusLine());
                log.info("Response Body: {}", responseBody);
            }
            return yandexData;

        } catch (IOException e) {
            log.error("У пользователя {} произошла ошибка: {}", chatId, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}