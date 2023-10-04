package com.kuzmichev.AdMetricsBot.service.yandex;

import com.kuzmichev.AdMetricsBot.model.Yandex;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YandexMainRequest {
    YandexRepository yandexRepository;

    public int yandexMainRequest(String projectId){
        Yandex yandex = yandexRepository.findByProjectId(projectId);
        String bearer = yandex.getYandexToken();

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api.direct.yandex.com/json/v5/reports");
        request.addHeader("Authorization", "Bearer " + bearer);
        request.addHeader("Accept-Language", "en");
        request.addHeader("method", "post");
        request.addHeader("content-type", "application/json; charset=utf-8");
        request.addHeader("returnMoneyInMicros", "false");
        StringEntity entity;
        String requestBody;
        String responseBody;
        try {
            entity = new StringEntity("""
                        {
                            "params": {
                              "SelectionCriteria": {
                              },
                              "FieldNames": [ "Date", "Cost" ],\s
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
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);

            HttpEntity entityResponse = response.getEntity();
            responseBody = EntityUtils.toString(entityResponse);

            HttpEntity entity2 = request.getEntity(); //????
            requestBody = EntityUtils.toString(entity2);

            int statusCode = response.getStatusLine().getStatusCode();

            String pattern = "\\s(\\d+\\.\\d+)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(responseBody);

            if (m.find()) {
                String result = m.group(1);
                double value = Double.parseDouble(result);
                return (int) value;
            } else if(statusCode == 200 && responseBody.contains("Total rows: 0")) {
                return 0;
            } else {
                log.error("Ответ не содержал данных");
                log.info("Request: {}", request);
                log.info("Request Headers: {}", Arrays.toString(request.getAllHeaders()));
                log.info("Request Body: {}", requestBody);
                log.info("Response: {}", response);
                log.info("Response Status Line: {}", response.getStatusLine());
                log.info("Response Body: {}", responseBody);

                return -1;
            }
        } catch (IOException e) {
            log.error("У пользователя " + yandex.getChatId() + " произошла ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}