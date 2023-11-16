package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

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
public class InputCallbackHandlersList {
    YandexCallbackHandler yandexCallbackHandler;
    BitrixCallbackHandler bitrixCallbackHandler;
    AddInputsCallbackHandler addInputsCallbackHandler;
    YclientsCallbackHandler yclientsCallbackHandler;
    YandexTestCallbackHandler yandexTestCallbackHandler;
    BitrixTestCallbackHandler bitrixTestCallbackHandler;
    YclientsTestCallbackHandler yclientsTestCallbackHandler;

    public List<CallbackHandler> getCallbackHandlers() {
        return Arrays.asList(
                yandexCallbackHandler,
                bitrixCallbackHandler,
                addInputsCallbackHandler,
                yclientsCallbackHandler,
                yandexTestCallbackHandler,
                bitrixTestCallbackHandler,
                yclientsTestCallbackHandler
        );
    }
}
