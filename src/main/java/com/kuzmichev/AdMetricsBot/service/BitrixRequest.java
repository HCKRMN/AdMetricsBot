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
import java.util.List;


public class BitrixRequest {
        public BitrixRequest() {
        }
        public static void main(String[] args) {
            Client client = new Client("yr1bx1oj0jdl1a0q", "myvis.bitrix24.ru", 7);
            List<Lead> leads = client.leadService().getAll();
            System.out.println(leads);
        }

//       https://myvis.bitrix24.ru/rest/7/yr1bx1oj0jdl1a0q/crm.lead.list


//    public static void main(String[] args) throws Exception {
//        String domain = "myvis.bitrix24.ru";
//        String accessToken = "yr1bx1oj0jdl1a0q";
//
//        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
//        String dateFrom = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
//        String dateTo = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59"));
//
//        String url = String.format("https://%s/rest/crm.lead.list?auth=%s&filter[DATE_CREATE]=range&filter[DATE_CREATE_from]=%s&filter[DATE_CREATE_to]=%s", domain, accessToken, dateFrom, dateTo);
//
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//
//        int responseCode = con.getResponseCode();
//        if (responseCode == 200) {
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            JSONObject json = new JSONObject(response.toString());
//            JSONArray leads = json.getJSONArray("result");
//            int count = leads.length();
//
//            System.out.println("Number of leads created yesterday: " + count);
//        } else {
//            System.out.println("Request failed with response code: " + responseCode);
//        }
//    }





    }
