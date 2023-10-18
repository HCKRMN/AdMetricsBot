package com.kuzmichev.AdMetricsBot.service.yandex;

import com.kuzmichev.AdMetricsBot.model.Yandex;
import com.kuzmichev.AdMetricsBot.model.YandexData;
import com.kuzmichev.AdMetricsBot.model.YandexRepository;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class YandexMainRequestTest {

    @Mock
    private YandexRepository yandexRepository;

    @InjectMocks
    private YandexMainRequest yandexMainRequest;

    @BeforeEach
    public void setUp() {
        // Мокирование данных, возвращаемых из репозитория
        Yandex yandex = new Yandex();
        yandex.setChatId("testChatId");
        yandex.setYandexToken("testToken");
        when(yandexRepository.findByProjectId(anyString())).thenReturn(yandex);
    }

    @Test
    public void testYandexMainRequest_Success_rows_1() throws Exception {
        // 1. Мокирование HTTP-клиента
        HttpClient mockHttpClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);
        HttpEntity mockEntity = mock(HttpEntity.class);

        // Успешный ответ содержит следующий JSON
        String mockResponseBody = """
                Date\tImpressions\tCtr\tClicks\tAvgCpc\tConversions\tCostPerConversion\tCost
                2023-10-17\t83\t7.23\t6\t135.76\t4\t203.64\t814.57
                Total rows: 1""";

        when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);
        when(mockResponse.getEntity()).thenReturn(mockEntity);
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream(mockResponseBody.getBytes()));
        when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        YandexMainRequest yandexMainRequest = new YandexMainRequest(yandexRepository);

        // Внедряем мокированный HTTP-клиент в yandexMainRequest
        Field httpClientField = YandexMainRequest.class.getDeclaredField("httpClient");
        httpClientField.setAccessible(true);
        httpClientField.set(yandexMainRequest, mockHttpClient);

        // 2. Вызов метода yandexMainRequest и проверка результата
        YandexData result = yandexMainRequest.yandexMainRequest("test");

        // Проверяем результат
        assertEquals(83 , result.getImpressions());
        assertEquals(7.23 , result.getCtr());
        assertEquals(6 , result.getClicks());
        assertEquals(135.76 , result.getAvgCpc());
        assertEquals(4 , result.getConversions());
        assertEquals(203.64 , result.getCostPerConversion());
        assertEquals(814.57 , result.getCost());
        assertEquals(200 , result.getRequestStatus());
    }

    @Test
    public void testYandexMainRequest_Success_rows_0() throws Exception {
        // 1. Мокирование HTTP-клиента
        HttpClient mockHttpClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);
        HttpEntity mockEntity = mock(HttpEntity.class);

        // Успешный ответ содержит следующий JSON
        String mockResponseBody = """
                Date\tImpressions\tCtr\tClicks\tAvgCpc\tConversions\tCostPerConversion\tCost
                Total rows: 0""";

        when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);
        when(mockResponse.getEntity()).thenReturn(mockEntity);
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream(mockResponseBody.getBytes()));
        when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        YandexMainRequest yandexMainRequest = new YandexMainRequest(yandexRepository);

        // Внедряем мокированный HTTP-клиент в yandexMainRequest
        Field httpClientField = YandexMainRequest.class.getDeclaredField("httpClient");
        httpClientField.setAccessible(true);
        httpClientField.set(yandexMainRequest, mockHttpClient);

        // 2. Вызов метода yandexMainRequest и проверка результата
        YandexData result = yandexMainRequest.yandexMainRequest("test");

        // Проверяем результат
        assertEquals(0 , result.getImpressions());
        assertEquals(0 , result.getCtr());
        assertEquals(0 , result.getClicks());
        assertEquals(0 , result.getAvgCpc());
        assertEquals(0 , result.getConversions());
        assertEquals(0 , result.getCostPerConversion());
        assertEquals(0 , result.getCost());
        assertEquals(200 , result.getRequestStatus());
    }

    @Test
    public void testYandexMainRequest_Error_Random_error_code() throws Exception {

        // 1. Мокирование HTTP-клиента
        HttpClient mockHttpClient = mock(HttpClient.class);
        HttpResponse mockResponse = mock(HttpResponse.class);
        HttpEntity mockEntity = mock(HttpEntity.class);

        int errorCode = 1 + (int) (Math.random() * 999);

        // Успешный ответ содержит следующий JSON
        String mockResponseBody = "{\"error\":{\"error_code\":\"" + errorCode +"\",\"error_string\":\"error\",\"error_detail\":\"Invalid\",\"request_id\":\"123\"}}";

        when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);
        when(mockResponse.getEntity()).thenReturn(mockEntity);
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream(mockResponseBody.getBytes()));
        when(mockResponse.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 400, "OK"));

        YandexMainRequest yandexMainRequest = new YandexMainRequest(yandexRepository);

        // Внедряем мокированный HTTP-клиент в yandexMainRequest
        Field httpClientField = YandexMainRequest.class.getDeclaredField("httpClient");
        httpClientField.setAccessible(true);
        httpClientField.set(yandexMainRequest, mockHttpClient);

        // 2. Вызов метода yandexMainRequest и проверка результата
        YandexData result = yandexMainRequest.yandexMainRequest("test");

        // Проверяем результат
        assertEquals(0 , result.getImpressions());
        assertEquals(0 , result.getCtr());
        assertEquals(0 , result.getClicks());
        assertEquals(0 , result.getAvgCpc());
        assertEquals(0 , result.getConversions());
        assertEquals(0 , result.getCostPerConversion());
        assertEquals(0 , result.getCost());
        assertEquals(errorCode , result.getRequestStatus());
    }

}