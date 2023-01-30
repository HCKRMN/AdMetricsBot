package com.kuzmichev.AdMetricsBot.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class YandexDirectExample {
    public String ya() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api-sandbox.direct.yandex.com/json/v5/reports");
        request.addHeader("Authorization", "Bearer y0_AgAAAAACVylaAAkJwAAAAADagRgL11wIBrVJR2GH2HzqFP0uzDEDKPI");
        request.addHeader("Accept-Language", "en");
        request.addHeader("Client-Login", "kauhax");
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
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer tempResult = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            tempResult.append(line);
        }
        String pattern = "\\s(\\d+\\.\\d+)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(tempResult);

        if (m.find()) {
            return m.group(1);
        } else {
            System.out.println("No match found.");
            return "-1";
        }
    }
}
