package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.kuzmichev.AdMetricsBot.model.Bitrix;
import com.kuzmichev.AdMetricsBot.model.BitrixData;
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
    BitrixCounterNewEntries bitrixCounterNewEntries;
    RefreshTokenRequest refreshTokenRequest;
    BitrixCounterFailedDeals bitrixCounterFailedDeals;
    BitrixCounterSuccessDeals bitrixCounterSuccessDeals;
    @SneakyThrows
    public BitrixData bitrixMainRequest(String projectId){
        Bitrix bitrix = bitrixRepository.findBitrixByProjectId(projectId);
        String accessToken = bitrix.getAccessToken();
        String domain = bitrix.getBitrixDomain();
        String chatId = bitrix.getChatId();
        int responseCode = testConnectionRequest.testConnectionRequest(bitrix, chatId);
        BitrixData bitrixData = new BitrixData();
        bitrixData.setProjectId(projectId);

         if (responseCode == 200){
             bitrixData.setNewLeads(bitrixCounterNewEntries.countRecordsRequest(accessToken, domain, chatId));
             bitrixData.setFailedDeals(bitrixCounterFailedDeals.countFailedDealsRequest(accessToken, domain, chatId));
             bitrixData.setSuccessDeals(bitrixCounterSuccessDeals.countSuccessDealsRequest(accessToken, domain, chatId));
             bitrixData.setRequestStatus(200);

         } else if (responseCode == 401){
             log.info("User " + bitrix.getChatId() + ". Выполняю обновление токена bitrix");
             accessToken = refreshTokenRequest.refreshTokenRequest(bitrix);
             bitrixData.setNewLeads(bitrixCounterNewEntries.countRecordsRequest(accessToken, domain, chatId));
             bitrixData.setFailedDeals(bitrixCounterFailedDeals.countFailedDealsRequest(accessToken, domain, chatId));
             bitrixData.setSuccessDeals(bitrixCounterSuccessDeals.countSuccessDealsRequest(accessToken, domain, chatId));
             bitrixData.setRequestStatus(200);
        } else {
             bitrixData.setChatId(chatId);
             bitrixData.setRequestStatus(responseCode);
        }
        return bitrixData;
    }
}
