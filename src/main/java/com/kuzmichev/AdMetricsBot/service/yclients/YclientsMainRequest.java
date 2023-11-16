package com.kuzmichev.AdMetricsBot.service.yclients;

import com.kuzmichev.AdMetricsBot.model.Yclients;
import com.kuzmichev.AdMetricsBot.model.YclientsData;
import com.kuzmichev.AdMetricsBot.model.YclientsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsMainRequest {
    YclientsRepository yclientsRepository;
    YclientsCounterNewEntries yclientsCounterNewEntries;
    YclientsCounterSuccessEntries yclientsCounterSuccessEntries;
    YclientsCounterCanceledEntries yclientsCounterCanceledEntries;
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
            yclientsData.setChatId(chatId);
            yclientsData.setProjectId(projectId);
            yclientsData.setNewEntries(yclientsCounterNewEntries.yclientsCounterNewEntries(salonId));
            yclientsData.setSuccessEntries(yclientsCounterSuccessEntries.yclientsCounterSuccessEntries(salonId));
            yclientsData.setCanceledEntries(yclientsCounterCanceledEntries.yclientsCounterCanceledEntries(salonId));
            yclientsData.setRequestStatus(200);
        } else {
            yclientsData.setChatId(chatId);
            yclientsData.setProjectId(projectId);
            yclientsData.setRequestStatus(-1);
            yclientsData.setRequestErrorMessage(testConnection);
        }
        return yclientsData;
    }
}
