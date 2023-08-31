package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.model.TempData;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TempDataSaver {
    TempDataRepository tempDataRepository;

    public void tempLastMessageId(String chatId, int messageId) {
        Optional<TempData> tempDataOptional = tempDataRepository.findByChatId(chatId);
        TempData tempData;
        if (tempDataOptional.isPresent()) {
            tempData = tempDataOptional.get();
        } else {
            tempData = new TempData();
            tempData.setChatId(chatId);
        }
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
