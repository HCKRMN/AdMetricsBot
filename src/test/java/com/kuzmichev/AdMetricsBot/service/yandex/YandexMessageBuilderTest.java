package com.kuzmichev.AdMetricsBot.service.yandex;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.model.YandexData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YandexMessageBuilderTest {

    private YandexMessageBuilder yandexMessageBuilder;
    private YandexMainRequest yandexMainRequest;

    @BeforeEach
    public void setUp() {
        yandexMainRequest = mock(YandexMainRequest.class);
        yandexMessageBuilder = new YandexMessageBuilder(yandexMainRequest);
    }

    @Test
    public void testGetMessage_Success() {
        YandexData mockData = new YandexData();
        mockData.setRequestStatus(200);
        mockData.setImpressions(83);
        mockData.setClicks(6);
        mockData.setCtr(7.23);
        mockData.setAvgCpc(135.76);
        mockData.setConversions(4);
        mockData.setCostPerConversion(203.64);
        mockData.setCost(814.57);

        when(yandexMainRequest.yandexMainRequest("someProjectId")).thenReturn(mockData);

        String expectedMessage = """
                Яндекс
                <code>Показы:             </code>83
                <code>Клики:              </code>6
                <code>CTR:                </code>7.23
                <code>CPC:                </code>135.76
                <code>Конверсии:          </code>4
                <code>CPA:                </code>203.64
                <code>Расход:             </code>814.57
                """;

        String actualMessage = yandexMessageBuilder.getMessage("someProjectId");

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetMessage_Error_513() {
        YandexData mockData = new YandexData();
        mockData.setRequestStatus(513);
        mockData.setImpressions(0);
        mockData.setClicks(0);
        mockData.setCtr(0);
        mockData.setAvgCpc(0);
        mockData.setConversions(0);
        mockData.setCostPerConversion(0);
        mockData.setCost(0);

        when(yandexMainRequest.yandexMainRequest("someProjectId")).thenReturn(mockData);

        String expectedMessage = "Яндекс\n" +
                MessageEnum.YANDEX_ERROR_513_MESSAGE.getMessage() +
                "\n";

        String actualMessage = yandexMessageBuilder.getMessage("someProjectId");

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetMessage_Error_53() {
        YandexData mockData = new YandexData();
        mockData.setRequestStatus(53);
        mockData.setImpressions(0);
        mockData.setClicks(0);
        mockData.setCtr(0);
        mockData.setAvgCpc(0);
        mockData.setConversions(0);
        mockData.setCostPerConversion(0);
        mockData.setCost(0);

        when(yandexMainRequest.yandexMainRequest("someProjectId")).thenReturn(mockData);

        String expectedMessage = "Яндекс\n" +
                MessageEnum.YANDEX_ERROR_53_MESSAGE.getMessage() +
                "\n";

        String actualMessage = yandexMessageBuilder.getMessage("someProjectId");

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetMessage_Error_Random_error_code() {
        int errorCode = 1 + (int) (Math.random() * 999);
        YandexData mockData = new YandexData();
        mockData.setRequestStatus(errorCode);
        mockData.setImpressions(0);
        mockData.setClicks(0);
        mockData.setCtr(0);
        mockData.setAvgCpc(0);
        mockData.setConversions(0);
        mockData.setCostPerConversion(0);
        mockData.setCost(0);

        when(yandexMainRequest.yandexMainRequest("someProjectId")).thenReturn(mockData);

        String expectedMessage = "Яндекс\n" +
                MessageEnum.YANDEX_ERROR_UNKNOWN_MESSAGE.getMessage() +
                "\n";

        String actualMessage = yandexMessageBuilder.getMessage("someProjectId");

        assertEquals(expectedMessage, actualMessage);
    }
}
