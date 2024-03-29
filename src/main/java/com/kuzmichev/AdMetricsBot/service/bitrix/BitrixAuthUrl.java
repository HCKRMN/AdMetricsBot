package com.kuzmichev.AdMetricsBot.service.bitrix;

import com.kuzmichev.AdMetricsBot.model.BitrixRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BitrixAuthUrl {
    final BitrixRepository bitrixRepository;
    @Value("${bitrixClientID}")
    String clientId;
    @Value("${telegram.webhook-path}")
    String link;
    @Value("${bitrixRedirectURL}")
    String redirectUri;

    public String getBitrixAuthorizationUrl(String chatId, String projectId, String userState) {
        String bitrixDomain = bitrixRepository.getBitrixDomainByProjectId(projectId);
        return "https://" + bitrixDomain +
                "/oauth/authorize/?client_id=" + clientId +
                "&response_type=code&redirect_uri=" + link + redirectUri +
                "&state=" + chatId + ":" + projectId + ":" + userState;
    }
}
