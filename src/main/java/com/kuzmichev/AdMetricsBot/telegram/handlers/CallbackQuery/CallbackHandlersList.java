package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery;

import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.Inputs.InputCallbackHandlersList;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.Menu.*;
import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.Projects.ProjectCallbackHandlersList;
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
    private final List<CallbackHandler> callbackHandlers;

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
