package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.YaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public
class YandexDirectRequest {
    public static String ya(YaRepository yaRepository, String chatId) throws IOException {
        String bearer = yaRepository.findById(chatId).get().getYaToken();

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api-sandbox.direct.yandex.com/json/v5/reports");
        request.addHeader("Authorization", "Bearer " + bearer);
        request.addHeader("Accept-Language", "en");
//        request.addHeader("Client-Login", "kauhax");
        request.addHeader("method", "post");
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.addHeader("returnMoneyInMicros", "false");
        request.addHeader("skipReportHeader", "true");
        request.addHeader("skipColumnHeader", "true");
        request.addHeader("skipReportSummary", "true");
        StringEntity entity = new StringEntity("""
					{
					    "params": {
					      "SelectionCriteria": {
					        "Filter": [{
					          "Field": "Conversions",
					          "Operator": "GREATER_THAN",
					          "Values": [ "0" ]
					        }]
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

                    // Log the request
                    log.info("Request: {}", request.getRequestLine());

                    // Get the response body
                    HttpEntity entity1 = response.getEntity();
                    String responseBody = EntityUtils.toString(entity1);

                    // Log the response
                    log.info("Response: {}", response.getStatusLine());
                    log.info("Response Body: {}", responseBody);

                    // Print the response to console
                    System.out.println("Request: " + request.getRequestLine());
                    System.out.println("Response: " + response.getStatusLine());
                    System.out.println("Response Body: " + responseBody);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));


        StringBuffer tempResult = new StringBuffer();
        String line = "";
        System.out.println(line);
        while ((line = rd.readLine()) != null) {
            tempResult.append(line);
        }
        String pattern = "\\s(\\d+\\.\\d+)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(tempResult);

        if (m.find()) {
            return m.group(1);
        } else {
            log.error("Ответ не содержал данных");
            return "Произошла ошибка";
        }
    }
}
