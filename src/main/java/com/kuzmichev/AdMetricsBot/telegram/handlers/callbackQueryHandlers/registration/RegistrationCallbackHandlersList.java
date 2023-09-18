package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.registration;

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
public class RegistrationCallbackHandlersList {
    RegistrationCallbackHandler registrationCallbackHandler;

    public List<CallbackHandler> getCallbackHandlers() {
        List<CallbackHandler> callbackHandlers = new ArrayList<>();
        callbackHandlers.add(registrationCallbackHandler);
        return callbackHandlers;
    }
}
