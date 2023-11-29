package com.kuzmichev.AdMetricsBot.telegram.handlers.messageHandlers;

import com.kuzmichev.AdMetricsBot.constants.CommandEnum;
import com.kuzmichev.AdMetricsBot.constants.InputsEnum;
import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.ProjectRepository;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.model.Yclients;
import com.kuzmichev.AdMetricsBot.model.YclientsRepository;
import com.kuzmichev.AdMetricsBot.service.MetricsMessageBuilder;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.*;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project.ProjectCreateOrListKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Registration;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.ProjectsDataTempKeeper;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommandHandler {
    SettingsKeyboard settingsKeyboard;
    StartRegistrationKeyboard startRegistrationKeyboard;
    Registration registration;
    MetricsMessageBuilder metricsMessageBuilder;
    UserStateKeeper userStateKeeper;
    CloseButtonKeyboard closeButtonKeyboard;
    YclientsRepository yclientsRepository;
    ProjectsDataTempKeeper projectsDataTempKeeper;
    YclientsTestKeyboard yclientsTestKeyboard;
    BackAndExitKeyboard backAndExitKeyboard;
    UserRepository userRepository;
    ProjectRepository projectRepository;
    ProjectCreateOrListKeyboard projectCreateOrListKeyboard;

    public BotApiMethod<?> handleUserCommand(Message message, String userState) {
        String chatId = message.getChatId().toString();
        String messageText = message.getText();
        String userName = message.getFrom().getUserName();
        String startData = "";

        if(messageText.contains(InputsEnum.Yclients.getInputName())){
            startData = messageText.substring(messageText.indexOf('_') + 1);
            messageText = CommandEnum.YCLIENTS.getCommand();
        }

        if(messageText.contains(" ")){
            startData = messageText.substring(messageText.indexOf(' ') + 1);
            messageText = CommandEnum.ERROR.getCommand();
        }

        CommandEnum commandEnum = CommandEnum.fromCommand(messageText);
        if (commandEnum != null) {
            switch (commandEnum) {
                case START -> {
                    userStateKeeper.setState(chatId, StateEnum.REGISTRATION_STATE.getStateName());
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(MessageEnum.START_MESSAGE.getMessage())
                            .replyMarkup(startRegistrationKeyboard.getKeyboard(chatId, userState))
                            .build();
                }

                case SETTINGS -> {
                    userStateKeeper.setState(chatId, StateEnum.SETTINGS_STATE.getStateName());
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(MessageEnum.SETTINGS_MENU_MESSAGE.getMessage())
                            .replyMarkup(settingsKeyboard.getKeyboard(chatId, userState))
                            .build();
                }

                case HELP -> {
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(MessageEnum.HELP_MESSAGE.getMessage())
                            .replyMarkup(closeButtonKeyboard.closeButtonKeyboard())
                            .build();
                }

                case REGISTER -> {
                    registration.registerUser(chatId, userName);
                    userStateKeeper.setState(chatId, StateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName());
                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(MessageEnum.PROJECT_CREATE_ASK_NAME_MESSAGE.getMessage())
                            .build();
                }

                case TEST -> {
                    return SendMessage.builder()
                            .chatId(chatId)
                            .parseMode("HTML")
                            .text(metricsMessageBuilder.getAllProjectsMessage(chatId))
                            .replyMarkup(closeButtonKeyboard.closeButtonKeyboard())
                            .build();
                }

                case YCLIENTS -> {
                    String projectId = projectsDataTempKeeper.getLastProjectId(chatId);
                    // Если у юзера создан проект и висит во временных данных
                    if (projectId != null){
                        Yclients yclients = yclientsRepository.findYclientsByYclientsId(startData);
                        yclients.setProjectId(projectId);
                        yclients.setChatId(chatId);
                        yclientsRepository.save(yclients);

                        return SendMessage.builder()
                                .chatId(chatId)
                                .text(MessageEnum.INPUT_TEST_MESSAGE.getMessage())
                                .replyMarkup(yclientsTestKeyboard.getKeyboard(chatId, userState))
                                .build();
                    } else { // Если у юзера не создан проект, значит он пришел из маркета yclients
                        registration.registerUser(chatId, userName);

                        Yclients yclients = yclientsRepository.findYclientsByYclientsId(startData);
                        yclients.setChatId(chatId);
                        yclientsRepository.save(yclients);

                        int projectCount = projectRepository.findProjectsCountByChatId(chatId);

                        // Если нет проектов, то пусть сразу вводит название и подключается
                        if (projectCount == 0){
                            if (userState.contains(StateEnum.REGISTRATION.getStateName())){
                                userStateKeeper.setState(chatId, StateEnum.REGISTRATION_PROJECT_CREATE_ASK_NAME_STATE.getStateName());
                            } else {
                                userStateKeeper.setState(chatId, StateEnum.SETTINGS_PROJECT_CREATE_ASK_NAME_STATE.getStateName());
                            }

                            return SendMessage.builder()
                                    .chatId(chatId)
                                    .text(MessageEnum.PROJECT_CREATE_ASK_NAME_MESSAGE.getMessage())
                                    .build();

                        } else {
                            return SendMessage.builder()
                                    .chatId(chatId)
                                    .text(MessageEnum.PROJECT_CREATE_OR_LIST_MESSAGE.getMessage())
                                    .replyMarkup(projectCreateOrListKeyboard.getKeyboard(chatId, userState))
                                    .build();
                        }
                    }
                }

                case ERROR -> {
                    String text = startData.equals("403") ? MessageEnum.ERROR_403_MESSAGE.getMessage() : MessageEnum.UNKNOWN_ERROR_MESSAGE.getMessage();

                    return SendMessage.builder()
                            .chatId(chatId)
                            .text(text)
                            .replyMarkup(closeButtonKeyboard.closeButtonKeyboard())
                            .build();
                }

                default -> {
                    return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
                }
            }
        }
        return new SendMessage(chatId, MessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}
