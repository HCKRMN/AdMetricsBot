package com.kuzmichev.AdMetricsBot.telegram.utils;

import com.kuzmichev.AdMetricsBot.constants.BotMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.ButtonNameEnum;
import com.kuzmichev.AdMetricsBot.constants.CallBackEnum;
import com.kuzmichev.AdMetricsBot.constants.UserStateEnum;
import com.kuzmichev.AdMetricsBot.model.Project;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.model.TempData;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.InlineKeyboardMaker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.UUID;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectManager {
    InlineKeyboardMaker inlineKeyboardMaker;
    UserStateEditor userStateEditor;
    BotMessageUtils botMessageUtils;
    ProjectRepository projectRepository;
    TempDataRepository tempDataRepository;

        // Это вроде для регистрации
    public SendMessage projectCreateStarter(String chatId) {

        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.PROJECT_CREATE_START_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(
                inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(
                                inlineKeyboardMaker.addRow(
                                        // Кнопка создания проекта
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.PROJECT_CREATE_BUTTON.getButtonName(),
                                                CallBackEnum.PROJECT_CREATE_ASK_NAME_CALLBACK,
                                                null
                                        )
                                )
                        )
                )
        );

        return sendMessage;
    }

        // Запрашиваем имя проекта и меняем состояние пользователя
    public SendMessage projectCreateAskName(String chatId) {
        userStateEditor.editUserState(chatId, UserStateEnum.PROJECT_CREATE_NAME_STATE);
        return new SendMessage(chatId, BotMessageEnum.PROJECT_NAME_ASK_MESSAGE.getMessage());
    }

        // Добавляем проект в базу
    public SendMessage projectCreate(String chatId, String messageText) {

        // Генерируем UUID и переводим в текст
        String projectId = UUID.randomUUID().toString();

        Project project = new Project();
        project.setProjectName(messageText);
        project.setChatId(chatId);
        project.setProjectId(projectId);
        projectRepository.save(project);

        TempData tempData = new TempData();
        tempData.setTempValue(projectId);
        tempData.setChatId(chatId);
        tempDataRepository.save(tempData);

        botMessageUtils.sendMessage(chatId, BotMessageEnum.PROJECT_CREATE_DONE_MESSAGE.getMessage());

        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.ADD_TOKENS_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(
                inlineKeyboardMaker.addMarkup(
                        inlineKeyboardMaker.addRows(
                                inlineKeyboardMaker.addRow(
                                        // Кнопка создания проекта
                                        inlineKeyboardMaker.addButton(
                                                ButtonNameEnum.ADD_YANDEX_BUTTON.getButtonName(),
                                                CallBackEnum.ADD_YANDEX_CALLBACK,
                                                null
                                        )
                                )
                        )
                )
        );

        return sendMessage;
    }

}
