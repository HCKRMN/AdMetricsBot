package com.kuzmichev.AdMetricsBot.telegram.utils.Messages;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageManagementService{
    MessageWithoutReturn messageWithoutReturn;
    HashMap<String, Set<Integer>> queueForDeletion = new HashMap<>();

    public void putMessageToQueue(String chatId, Integer messageId) {
        System.out.println("Добавляем сообщение: " + messageId);
        queueForDeletion.computeIfAbsent(chatId, k -> new HashSet<>()).add(messageId);

        Set<Integer> messageIdSet = queueForDeletion.get(chatId);
        System.out.println("Теперь в очереди на удаление: " + messageIdSet.toString());
    }

    public void deleteMessage(String chatId) {
        System.out.println("Начинаем удаление");
        if(queueForDeletion.containsKey(chatId)) {
            Set<Integer> messageIdSet = queueForDeletion.get(chatId);
            System.out.println("Список на удаление: " + messageIdSet.toString());
            for (Integer messageId : messageIdSet) {
                System.out.println("Удаляем сообщение: " + messageId);
                messageWithoutReturn.deleteMessage(chatId, messageId);
            }
            queueForDeletion.remove(chatId);
        }
    }

    public int getLastMessageId(String chatId){
        if (queueForDeletion.containsKey(chatId)){
            Set<Integer> messageIdSet = queueForDeletion.get(chatId);
            return Collections.max(messageIdSet);
        }
        return -1;
    }

    public void removeMessageFromQueue(String chatId, int messageId){
        if(queueForDeletion.containsKey(chatId)) {
            System.out.println("Удаляем закрытое сообщение из очереди: " + messageId);
            queueForDeletion.get(chatId).remove(messageId);

            Set<Integer> messageIdSet = queueForDeletion.get(chatId);
            System.out.println("Теперь в очереди на удаление: " + messageIdSet.toString());
        }

    }
}
