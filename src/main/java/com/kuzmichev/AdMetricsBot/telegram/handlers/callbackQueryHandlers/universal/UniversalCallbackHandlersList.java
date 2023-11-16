package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.universal;

import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UniversalCallbackHandlersList {
    EditTimeZoneCallbackHandler editTimeZoneCallbackHandler;
    EditTimeZoneManualCallbackHandler editTimeZoneManualCallbackHandler;

    public List<CallbackHandler> getCallbackHandlers() {
        return Arrays.asList(
                editTimeZoneCallbackHandler,
                editTimeZoneManualCallbackHandler
        );
    }
}
