package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationStateEnum;
import com.kuzmichev.AdMetricsBot.constants.settingsEnums.SettingsMessageEnum;
import com.kuzmichev.AdMetricsBot.constants.registrationEnums.RegistrationMessageEnum;
import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.service.IpToTimeZone;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.ProjectCreateKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserIPController {
    private final UserRepository userRepository;
    private final TempDataRepository tempDataRepository;
    private final MessageManagementService messageManagementService;
    private final MessageWithoutReturn messageWithoutReturn;
    private final IpToTimeZone ipToTimeZone;
    private final ProjectCreateKeyboard projectCreateKeyboard;
    private final DoneButtonKeyboard doneButtonKeyboard;

    @GetMapping("/getip")
    public String myEndpoint(HttpServletRequest request,
                             @RequestParam(name = "chatId") String chatId) {
        String ip = getIpAddress(request);
        double timeZone = ipToTimeZone.convertIpToTimeZone(ip);

        int messageId = tempDataRepository.findLastMessageIdByChatId(chatId);
        messageManagementService.putMessageToQueue(chatId, messageId);

        Optional<User> userOptional = userRepository.findByChatId(chatId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIp(ip);
            user.setTimeZone(timeZone);
            userRepository.save(user);
            if (user.getUserState().equals(RegistrationStateEnum.REGISTRATION_STATE.getStateName())) {
                messageWithoutReturn.sendMessage(
                        chatId,
                        RegistrationMessageEnum.REGISTRATION_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE.getMessage(),
                        projectCreateKeyboard.projectCreateKeyboard(user.getUserState())
                );
            } else {
                messageWithoutReturn.sendMessage(
                        chatId,
                        SettingsMessageEnum.SETTINGS_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE.getMessage(),
                        doneButtonKeyboard.doneButtonMenu()
                );
            }
                messageManagementService.deleteMessage(chatId);
        }
        return "getip";
    }


    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
