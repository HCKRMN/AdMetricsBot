package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.kuzmichev.AdMetricsBot.constants.universalEnums.UniversalMessageEnum;
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
    CountAllCreateRecordsRequest countAllCreateRecordsRequest;
    RefreshTokenRequest refreshTokenRequest;
    CountFailedDealsRequest countFailedDealsRequest;
    CountSuccessDealsRequest countSuccessDealsRequest;
    @SneakyThrows
    public BitrixData bitrixMainRequest(String projectId){
        Bitrix bitrix = bitrixRepository.findBitrixByProjectId(projectId);
        String accessToken = bitrix.getAccessToken();
        String domain = bitrix.getBitrixDomain();
        String chatId = bitrix.getChatId();
        int responseCode = testConnectionRequest.testConnectionRequest(bitrix);
        BitrixData bitrixData = new BitrixData();
        bitrixData.setProjectId(projectId);

         if (responseCode == 200){
             bitrixData.setNewLeads(countAllCreateRecordsRequest.countRecordsRequest(accessToken, domain, chatId));
             bitrixData.setFailedDeals(countFailedDealsRequest.countFailedDealsRequest(accessToken, domain, chatId));
             bitrixData.setSuccessDeals(countSuccessDealsRequest.countSuccessDealsRequest(accessToken, domain, chatId));
             bitrixData.setRequestStatus(1);
         } else if (responseCode == 401){
             log.info("User " + bitrix.getChatId() + ". Выполняю обновление токена bitrix");
             accessToken = refreshTokenRequest.refreshTokenRequest(bitrix);
             bitrixData.setNewLeads(countAllCreateRecordsRequest.countRecordsRequest(accessToken, domain, chatId));
             bitrixData.setFailedDeals(countFailedDealsRequest.countFailedDealsRequest(accessToken, domain, chatId));
             bitrixData.setSuccessDeals(countSuccessDealsRequest.countSuccessDealsRequest(accessToken, domain, chatId));
             bitrixData.setRequestStatus(1);
        } else {
             bitrixData.setRequestStatus(-1);
             log.error("User " + bitrix.getChatId() + ". Не удалось обновить токен bitrix, но тестовый запрос пройден");
        }
        return bitrixData;
    }
}
