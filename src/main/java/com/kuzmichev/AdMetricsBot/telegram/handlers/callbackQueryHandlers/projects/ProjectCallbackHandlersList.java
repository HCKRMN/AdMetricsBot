package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects.delete.DeleteInputCallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects.delete.DeleteProjectStepOneCallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects.delete.DeleteProjectStepTwoCallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects.delete.DeleteSomeInputCallbackHandler;
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
public class ProjectCallbackHandlersList {
    DeleteInputCallbackHandler deleteInputCallbackHandler;
    DeleteProjectStepOneCallbackHandler deleteProjectStepOneCallbackHandler;
    DeleteProjectStepTwoCallbackHandler deleteProjectStepTwoCallbackHandler;
    ProjectListCallBackHandler projectListCallBackHandler;
    ProjectsMenuCallbackHandlers projectsMenuCallbackHandlers;
    SomeProjectCallbackHandler someProjectCallbackHandler;
    ProjectCreateCallbackHandler projectCreateCallbackHandler;
    ProjectGetInfoCallbackHandler projectGetInfoCallbackHandler;
    DeleteSomeInputCallbackHandler deleteSomeInputCallbackHandler;

    public List<CallbackHandler> getCallbackHandlers() {
        return Arrays.asList(
                deleteInputCallbackHandler,
                deleteProjectStepOneCallbackHandler,
                projectListCallBackHandler,
                projectsMenuCallbackHandlers,
                someProjectCallbackHandler,
                projectCreateCallbackHandler,
                projectGetInfoCallbackHandler,
                deleteSomeInputCallbackHandler,
                deleteProjectStepTwoCallbackHandler
        );
    }
}
