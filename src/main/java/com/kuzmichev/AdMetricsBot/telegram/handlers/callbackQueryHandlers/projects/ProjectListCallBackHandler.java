package com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.projects;

import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsCallBackEnum;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project.ProjectListKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.CallbackHandler;
import com.kuzmichev.AdMetricsBot.telegram.handlers.callbackQueryHandlers.DynamicCallback;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithReturn;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectListCallBackHandler implements CallbackHandler {
    MessageWithReturn messageWithReturn;
    ProjectListKeyboard projectListKeyboard;
    UserRepository userRepository;

    @Override
    public boolean canHandle(String data) {
        return Objects.equals(data, SettingsCallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName())
               || data.contains("page_");
    }

    @Override
    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, String userState) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        String data = buttonQuery.getData();
        int messageId = buttonQuery.getMessage().getMessageId();
        int currentPage = 1;

        String regexPage = "page_.+";
        Map<String, String> pageData = DynamicCallback.handleDynamicCallback(data, regexPage, "page_");
        if (!pageData.isEmpty()) {
            currentPage = Integer.parseInt(pageData.get("value"));
            data = SettingsCallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName();
        }

        if (Objects.equals(data, SettingsCallBackEnum.PROJECT_GET_LIST_CALLBACK.getCallBackName())) {
            Optional<User> userOptional = userRepository.findByChatId(chatId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setProjectsPage(currentPage);
                userRepository.save(user);
            }
            return messageWithReturn.editMessage(
                    chatId,
                    messageId,
                    SettingsMessageEnum.SETTINGS_PROJECTS_LIST_MENU_MESSAGE.getMessage(),
                    null,
                    projectListKeyboard.projectListMenu(chatId, userState));
        }
        return new SendMessage(chatId, SettingsMessageEnum.NON_COMMAND_MESSAGE.getMessage());
    }
}

