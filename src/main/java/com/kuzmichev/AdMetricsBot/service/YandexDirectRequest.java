package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class YandexDirectRequest {
    public static String ya(YandexRepository yandexRepository, String chatId) throws IOException {
        String bearer = yandexRepository.findById(chatId).get().getYaToken();

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api.direct.yandex.com/json/v5/reports");
        request.addHeader("Authorization", "Bearer " + bearer);
        request.addHeader("Accept-Language", "en");
//        request.addHeader("Client-Login", "kauhax");
        request.addHeader("method", "post");
        request.addHeader("content-type", "application/json; charset=utf-8");
        request.addHeader("returnMoneyInMicros", "false");
//        request.addHeader("skipReportHeader", "true");
//        request.addHeader("skipColumnHeader", "true");
//        request.addHeader("skipReportSummary", "true");
        StringEntity entity = new StringEntity("""
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
        String responseBody = EntityUtils.toString(entityResponse);

//        Отладочная информация
        HttpEntity entity2 = request.getEntity();
        String requestBody = EntityUtils.toString(entity2);
        System.out.println(request);
        System.out.println(Arrays.toString(request.getAllHeaders()));
        System.out.println("Request Body: " + requestBody);
        System.out.println(response);
        System.out.println(response.getStatusLine());
        System.out.println("Response Body: " + responseBody);

        int statusCode = response.getStatusLine().getStatusCode();

        String pattern = "\\s(\\d+\\.\\d+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(responseBody);


        if (m.find()) {
            return m.group(1);
        } else if(statusCode == 200 && responseBody.contains("Total rows: 0")) {
            return "0";
        } else {
            log.error("Ответ не содержал данных");

            // Добавляем отладочную информацию в лог
            log.info("Request: {}", request);
            log.info("Request Headers: {}", Arrays.toString(request.getAllHeaders()));
            log.info("Request Body: {}", requestBody);
            log.info("Response: {}", response);
            log.info("Response Status Line: {}", response.getStatusLine());
            log.info("Response Body: {}", responseBody);

            return "-1";
        }



    }
}
