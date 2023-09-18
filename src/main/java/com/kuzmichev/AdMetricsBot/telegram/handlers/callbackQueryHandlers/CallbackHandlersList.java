package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers;

import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs.InputCallbackHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.*;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects.ProjectCallbackHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.registration.RegistrationCallbackHandlersList;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CallbackHandlersList {
    List<CallbackHandler> callbackHandlers;

    public CallbackHandlersList(
            MenuCallbackHandlersList menuCallbackHandlersList,
            ProjectCallbackHandlersList projectCallbackHandlersList,
            InputCallbackHandlersList inputCallbackHandlersList,
            RegistrationCallbackHandlersList registrationCallbackHandlersList
    ) {
        callbackHandlers = new ArrayList<>();
        callbackHandlers.addAll(menuCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(projectCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(inputCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(registrationCallbackHandlersList.getCallbackHandlers());
    }

    public List<CallbackHandler> getCallbackHandlers() {
        return callbackHandlers;
    }
}
