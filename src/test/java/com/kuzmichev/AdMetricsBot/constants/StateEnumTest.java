package com.kuzmichev.AdMetricsBot.constants;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateEnumTest {

    @Test
    public void testEnumNames() {
        for (StateEnum State : StateEnum.values()) {
            String expectedName = State.name();
            String actualName = State.getStateName();

            assertEquals(expectedName, actualName,
                    "Неправильный стейт: " + expectedName);
        }
    }
}
