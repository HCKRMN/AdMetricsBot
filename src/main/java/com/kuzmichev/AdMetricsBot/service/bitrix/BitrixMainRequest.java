package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixMainRequest {
    BitrixRepository bitrixRepository;
    TestConnectionRequest testConnectionRequest;
    CountRecordsRequest countRecordsRequest;
    RefreshTokenRequest refreshTokenRequest;
    @SneakyThrows
    public int mainBitrixRequest(String projectId){
        Bitrix bitrix = bitrixRepository.findBitrixByProjectId(projectId);
        String accessToken = bitrix.getAccessToken();
        String domain = bitrix.getBitrixDomain();
        String chatId = bitrix.getChatId();
        int responseCode = testConnectionRequest.testConnectionRequest(bitrix);

         if (responseCode == 200){
            return countRecordsRequest.countRecordsRequest(accessToken, domain, chatId);
         } else if (responseCode == 401){
            log.info("User " + bitrix.getChatId() + ". Выполняю обновление токена bitrix");
            accessToken = refreshTokenRequest.refreshTokenRequest(bitrix);
            return countRecordsRequest.countRecordsRequest(accessToken, domain, chatId);
        } else {
            log.error("User " + bitrix.getChatId() + ". Не удалось обновить токен bitrix, но тестовый запрос пройден");
            return -1;
        }
    }
}
