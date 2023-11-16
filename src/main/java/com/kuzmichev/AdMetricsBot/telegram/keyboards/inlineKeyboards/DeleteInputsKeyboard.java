package com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards;

import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.InputsEnum;
import com.kuzmichev.AdMetricsBot.telegram.utils.InputsManager;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DeleteInputsKeyboard {
    BackAndExitKeyboard backAndExitKeyboard;
    InputsManager inputsManager;
    ProjectsDataTempKeeper projectsDataTempKeeper;
    public InlineKeyboardMarkup deleteInputsMenu(String chatId, String userState) {
        String projectId = projectsDataTempKeeper.getLastProjectId(chatId);
        List<InputsEnum> inputs = inputsManager.getInputsByProjectId(projectId);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (InputsEnum input : inputs) {
            rows.add(List.of(InlineKeyboardButton.builder()
                    .text(input.getInputName())
                    .callbackData(CallBackEnum.INPUT_CALLBACK.getCallBackName() + input.getInputName())
                    .build()));
        }
        rows.add(backAndExitKeyboard.getButtons(userState));

        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }
}
