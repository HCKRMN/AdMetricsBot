//package com.kuzmichev.AdMetricsBot.telegram.utils.TempData;
//
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//
//@Slf4j
//@Component
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@RequiredArgsConstructor
//public class MessageDataTempKeeper {
//    HashMap<String, Integer> lastMessageId = new HashMap<>();
//    public void setLastMessageId(String chatId, int messageId){
//        lastMessageId.put(chatId,messageId);
//    }
//
//    public int getLastMessageId(String chatId){
//        return lastMessageId.get(chatId);
//    }
//}
