package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.TempData;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TempDataSaver {
    TempDataRepository tempDataRepository;

    public void tempMessageId(String chatId, int messageId) {
    TempData tempData = new TempData();
                tempData.setChatId(chatId);
                tempData.setLastMessageId(messageId);
                tempDataRepository.save(tempData);
    }

    public void tempMessagesIdsToDelete(String chatId, String messagesIdsToDelete) {
    TempData tempData = new TempData();
                tempData.setChatId(chatId);
                tempData.setMessagesIdsToDelete(messagesIdsToDelete);
                tempDataRepository.save(tempData);
    }

    public void tempProjectId(String chatId, String projectId) {
        TempData tempData = new TempData();
        tempData.setChatId(chatId);
        tempData.setLastProjectId(projectId);
        tempDataRepository.save(tempData);
    }
}
