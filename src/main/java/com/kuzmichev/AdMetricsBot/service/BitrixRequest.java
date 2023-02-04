package com.kuzmichev.AdMetricsBot.service;

import com.javastream.Client;
import com.javastream.entity.Lead;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BitrixRequest {
    public static int bi() throws Exception {
        String domain = "myvis.bitrix24.ru";
        String accessToken = "yr1bx1oj0jdl1a0q";
        int userId = 7;

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String dateFrom = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
        String dateTo = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59"));

        String url = String.format("https://%s/rest/%s/%s/crm.lead.list?filter[<DATE_CREATE]=%s&filter[>DATE_CREATE]=%s", domain, userId, accessToken, dateTo, dateFrom);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        int count = 0;
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray leads = json.getJSONArray("result");
            count = leads.length();

            System.out.println("Number of leads created yesterday: " + count);
        } else {
            System.out.println("Request failed with response code: " + responseCode);
        }
        return count;
    }
}
