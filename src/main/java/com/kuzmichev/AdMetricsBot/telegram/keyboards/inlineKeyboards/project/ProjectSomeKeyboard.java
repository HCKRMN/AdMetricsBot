package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project;

import com.kuzmichev.AdMetricsBot.constants.ButtonEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.BackAndExitKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.InlineKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.ExistInputsChecker;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectSomeKeyboard implements InlineKeyboard {
    BackAndExitKeyboard backAndExitKeyboard;
    ProjectsDataTempKeeper projectsDataTempKeeper;
    ExistInputsChecker existInputsChecker;
    public InlineKeyboardMarkup getKeyboard(String userState, String chatId) {

        String projectId = projectsDataTempKeeper.getLastProjectId(chatId);
        List<InlineKeyboardButton> deleteInputAndGetInfoButtons = List.of();

        if (existInputsChecker.isExist(projectId)) {
            deleteInputAndGetInfoButtons = List.of(
                    InlineKeyboardButton.builder()
                            .text(ButtonEnum.PROJECT_DELETE_TOKEN_BUTTON.getButtonName())
                            .callbackData(CallBackEnum.PROJECT_INPUT_DELETE_CALLBACK.getCallBackName())
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(ButtonEnum.PROJECT_GET_INFO_BUTTON.getButtonName())
                            .callbackData(CallBackEnum.PROJECT_GET_INFO_CALLBACK.getCallBackName())
                            .build()
            );
        }

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.PROJECT_ADD_TOKEN_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.ADD_INPUTS_CALLBACK.getCallBackName())
                        .build()))
                .keyboardRow(deleteInputAndGetInfoButtons)
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                        .text(ButtonEnum.PROJECT_DELETE_BUTTON.getButtonName())
                        .callbackData(CallBackEnum.PROJECT_DELETE_STEP_1_CALLBACK.getCallBackName())
                        .build()))
                .keyboardRow(backAndExitKeyboard.getButtons(userState))
                .build();
    }
}
