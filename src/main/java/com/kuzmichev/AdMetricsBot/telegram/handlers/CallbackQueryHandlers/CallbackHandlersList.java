package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers;

import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Inputs.InputCallbackHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Menu.*;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQueryHandlers.Projects.ProjectCallbackHandlersList;
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
            InputCallbackHandlersList inputCallbackHandlersList
    ) {
        callbackHandlers = new ArrayList<>();
        callbackHandlers.addAll(menuCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(projectCallbackHandlersList.getCallbackHandlers());
        callbackHandlers.addAll(inputCallbackHandlersList.getCallbackHandlers());
    }

    public List<CallbackHandler> getCallbackHandlers() {
        return callbackHandlers;
    }
}
