package com.kuzmichev.AdMetricsBot.service.yclients;

import com.kuzmichev.AdMetricsBot.model.Yclients;
import com.kuzmichev.AdMetricsBot.model.YclientsData;
import com.kuzmichev.AdMetricsBot.model.YclientsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsMainRequest {
    YclientsRepository yclientsRepository;
    YclientsCounterNewEntries yclientsCounterNewEntries;
    YclientsAnalyticsRequest yclientsAnalyticsRequest;
    YclientsTestConnection yclientsTestConnection;

    public YclientsData yclientsMainRequest(String projectId){
        Yclients yclients = yclientsRepository.findYclientsByProjectId(projectId);

        if(yclients == null){
            return null;
        }

        String salonId = yclients.getSalonId();
        String testConnection = yclientsTestConnection.yclientsTestConnection(salonId);
        String chatId = yclients.getChatId();
        YclientsData yclientsData = new YclientsData();

        if(testConnection.equals("200")){
            int requestStatus = 200;
            int successEntries = 0;
            int canceledEntries = 0;
            
            String responseWithAnalyticsData = yclientsAnalyticsRequest.yclientsAnalyticsRequest(salonId);
            
            if (responseWithAnalyticsData.contains("success\":false")) {
                requestStatus = -1;
                JSONObject jsonResponse = new JSONObject(responseWithAnalyticsData);
                yclientsData.setRequestErrorMessage(jsonResponse.getJSONObject("meta").getString("message"));
            } else {
                JSONObject responseWithAnalyticsDataJson = new JSONObject(responseWithAnalyticsData);
                successEntries = responseWithAnalyticsDataJson
                        .getJSONObject("data")
                        .getJSONObject("record_stats")
                        .getInt("current_completed_count");

                canceledEntries = responseWithAnalyticsDataJson
                        .getJSONObject("data")
                        .getJSONObject("record_stats")
                        .getInt("current_canceled_count");
            }

            yclientsData.setChatId(chatId);
            yclientsData.setProjectId(projectId);
            yclientsData.setNewEntries(yclientsCounterNewEntries.yclientsCounterNewEntries(salonId));
            yclientsData.setSuccessEntries(successEntries);
            yclientsData.setCanceledEntries(canceledEntries);
            yclientsData.setRequestStatus(requestStatus);
        } else {
            yclientsData.setChatId(chatId);
            yclientsData.setProjectId(projectId);
            yclientsData.setRequestStatus(-1);
            yclientsData.setRequestErrorMessage(testConnection);
        }
        return yclientsData;
    }
}
