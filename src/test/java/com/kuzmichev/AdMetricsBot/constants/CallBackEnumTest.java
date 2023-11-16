package com.kuzmichev.AdMetricsBot.constants;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallBackEnumTest {

    @Test
    public void testEnumNames() {
        for (CallBackEnum callback : CallBackEnum.values()) {
            String expectedName = callback.name();
            String actualName = callback.getCallBackName();

            // Учитываем особые случаи, если они есть
            if (expectedName.equals("PROJECT_PAGE_CALLBACK")) {
                expectedName = "page_";
            } else if (expectedName.equals("INPUT_CALLBACK")) {
                expectedName = "input_";
            }

            assertEquals(expectedName, actualName,
                    "Неправильный коллбек: " + expectedName);
        }
    }
}
