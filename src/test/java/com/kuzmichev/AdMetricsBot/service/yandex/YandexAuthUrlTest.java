package com.kuzmichev.AdMetricsBot.service.yandex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YandexAuthUrlTest {

    @Autowired
    private YandexAuthUrl yandexAuthUrl;

    @Value("${yandexClientID}")
    private String clientId;

    @Value("${telegram.webhook-path}")
    private String link;

    @Value("${yandexRedirectURI}")
    private String redirectUri;

    @Test
    public void testGetYandexAuthorizationUrl() {
        String chatId = "123456";
        String userState = "testUserState";
        String expectedUrl = "https://oauth.yandex.ru/authorize" +
                "?response_type=token" +
                "&client_id=" + clientId +
                "&redirect_uri=" + link + redirectUri +
                "&state=" + chatId + "_" + userState;

        String actualUrl = yandexAuthUrl.getYandexAuthorizationUrl(chatId, userState);

        assertEquals(expectedUrl, actualUrl);
    }
}
