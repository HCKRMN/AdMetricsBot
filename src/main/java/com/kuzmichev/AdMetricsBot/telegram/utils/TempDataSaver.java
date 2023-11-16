//package com.kuzmichev.AdMetricsBot.telegram.utils;
//
//import com.kuzmichev.AdMetricsBot.model.TempData;
//import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Slf4j
//@Component
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@RequiredArgsConstructor
//public class TempDataSaver {
//    TempDataRepository tempDataRepository;
//
//    public void tempLastMessageId(String chatId, int messageId) {
//        Optional<TempData> tempDataOptional = tempDataRepository.findByChatId(chatId);
//        TempData tempData = tempDataOptional.orElseGet(TempData::new);
//        tempData.setChatId(chatId);
//        tempData.setLastMessageId(messageId);
//        tempDataRepository.save(tempData);
//    }
//
//    public void tempLastProjectId(String chatId, String projectId) {
//        Optional<TempData> tempDataOptional = tempDataRepository.findByChatId(chatId);
//        TempData tempData = tempDataOptional.orElseGet(TempData::new);
//        tempData.setChatId(chatId);
//        tempData.setLastProjectId(projectId);
//        tempDataRepository.save(tempData);
//    }
//}
