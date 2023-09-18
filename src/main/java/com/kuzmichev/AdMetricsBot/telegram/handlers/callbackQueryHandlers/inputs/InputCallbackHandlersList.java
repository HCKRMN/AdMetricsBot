package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs;

import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class InputCallbackHandlersList {
    YandexCallbackHandler yandexCallbackHandler;
    BitrixCallbackHandler bitrixCallbackHandler;
    AddInputsCallbackHandler addInputsCallbackHandler;

    public List<CallbackHandler> getCallbackHandlers() {
        List<CallbackHandler> callbackHandlers = new ArrayList<>();
        callbackHandlers.add(yandexCallbackHandler);
        callbackHandlers.add(bitrixCallbackHandler);
        callbackHandlers.add(addInputsCallbackHandler);
        return callbackHandlers;
    }
}
