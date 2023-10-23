//package com.kuzmichev.AdMetricsBot.service.yclients;
//
//import jakarta.annotation.PostConstruct;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@RequiredArgsConstructor
//public class Yclients {
//    @Value("${company-id}")
//    long companyId;
//    @Value("${bearer-authentication}")
//    String bearerAuthentication;
//    @Value("${user-token}")
//    String userToken;
//    @Value("${content-type-header}")
//    String contentTypeHeader;
//    @Value("${accept-header}")
//    String acceptHeader;
//
//
//    @PostConstruct
//    public void getNumberOfRecordsForDate() throws IOException {
//
//        HttpPost request = new HttpPost("https://api.yclients.com/api/v1/records/" + companyId + "/");
//        request.addHeader("Authorization", "Bearer " + bearerAuthentication + ", User " + userToken);
//        request.addHeader("Accept", "application/vnd.yclients.v2+json");
//
//        HttpClient httpClient = HttpClientBuilder.create().build();
//
//        HttpResponse response = httpClient.execute(request);
//
//        // Получаем ответ
//        HttpEntity entityResponse = response.getEntity();
//        String responseBody = EntityUtils.toString(entityResponse);
//        int statusCode = response.getStatusLine().getStatusCode();
//
//        System.out.println("===========================================================");
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        System.out.println("statusCode: " + statusCode);
//        System.out.println("responseBody: " + responseBody);
//
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println("===========================================================");
//    }
//}