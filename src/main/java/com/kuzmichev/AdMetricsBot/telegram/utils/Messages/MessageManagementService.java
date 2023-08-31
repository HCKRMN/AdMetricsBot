package com.kuzmichev.AdMetricsBot.telegram.utils.Messages;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageManagementService{
    MessageWithoutReturn messageWithoutReturn;
    HashMap<String, List<Integer>> queueForDeletion = new HashMap<>();

    // Добавляем в очередь
    public void putMessageToQueue(String chatId, Integer messageId) {
        System.out.println("Кладем в очередь удаления: " + messageId);
        queueForDeletion.computeIfAbsent(chatId, k -> new ArrayList<>()).add(messageId);
    }

    // Удаляем сообщения
    public void deleteMessage(String chatId) {
        if(queueForDeletion.containsKey(chatId)) {
            List<Integer> messageIdList = queueForDeletion.get(chatId);
            for (Integer messageId : messageIdList) {
                System.out.println("Удаляем сообщение: " + messageId);
                messageWithoutReturn.deleteMessage(chatId, messageId);
            }
            queueForDeletion.remove(chatId);
        }
    }
}
