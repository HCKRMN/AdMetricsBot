package com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.Projects;

import com.kuzmichev.AdMetricsBot.telegram.handlers.CallbackQuery.CallbackHandler;
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
public class ProjectCallbackHandlersList {
    DeleteInputCallbackHandler deleteInputCallbackHandler;
    DeleteProjectCallbackHandler deleteProjectCallbackHandler;
    ProjectListCallBackHandler projectListCallBackHandler;
    ProjectsMenuCallbackHandlers projectsMenuCallbackHandlers;
    SomeProjectCallbackHandler someProjectCallbackHandler;
    ProjectCreateCallbackHandler projectCreateCallbackHandler;

    public List<CallbackHandler> getCallbackHandlers() {
        List<CallbackHandler> callbackHandlers = new ArrayList<>();
        callbackHandlers.add(deleteInputCallbackHandler);
        callbackHandlers.add(deleteProjectCallbackHandler);
        callbackHandlers.add(projectListCallBackHandler);
        callbackHandlers.add(projectsMenuCallbackHandlers);
        callbackHandlers.add(someProjectCallbackHandler);
        callbackHandlers.add(projectCreateCallbackHandler);
        return callbackHandlers;
    }
}
