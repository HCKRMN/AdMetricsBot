package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers;

import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.inputs.InputCallbackHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.menu.MenuCallbackHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects.ProjectCallbackHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.registration.RegistrationCallbackHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.universal.UniversalCallbackHandlersList;
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
            RegistrationCallbackHandlersList registrationCallbackHandlersList,
            UniversalCallbackHandlersList universalCallbackHandlersList
    ) {
        callbackHandlers = new ArrayList<>();
        callbackHandlers.addAll(menuCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(projectCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(inputCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(registrationCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(universalCallbackHandlersList.getCallbackHandlers());
    }

    public List<CallbackHandler> getCallbackHandlers() {
        return callbackHandlers;
    }
}
