package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.constants.MessageEnum;
import com.kuzmichev.AdMetricsBot.constants.StateEnum;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.service.IpToTimeZone;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.DoneButtonKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.keyboards.inlineKeyboards.project.ProjectCreateKeyboard;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageWithoutReturn;
import com.kuzmichev.AdMetricsBot.telegram.utils.TempData.UserStateKeeper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserIPController {
    UserRepository userRepository;
    MessageManagementService messageManagementService;
    MessageWithoutReturn messageWithoutReturn;
    IpToTimeZone ipToTimeZone;
    DoneButtonKeyboard doneButtonKeyboard;
    ProjectCreateKeyboard projectCreateKeyboard;
    UserStateKeeper userStateKeeper;

    @GetMapping("/getip")
    public String getUserIp(HttpServletRequest request,
                            @RequestParam(name = "chatId") String chatId) {
        String ip = getIpAddress(request);
        int timeZone = 0;
        try {
            timeZone = ipToTimeZone.convertIpToTimeZone(ip);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        Optional<User> userOptional = userRepository.findByChatId(chatId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIp(ip);
            user.setTimeDifferenceInMinutes(timeZone);
            userRepository.save(user);
            messageManagementService.deleteMessage(chatId);

            String userState = userStateKeeper.getState(chatId);

            if (userState.contains(StateEnum.REGISTRATION.getStateName())) {
                userStateKeeper.setState(chatId, StateEnum.REGISTRATION_EDIT_TIMEZONE_COMPLETE_STATE.getStateName());
                messageWithoutReturn.sendMessage(
                        chatId,
                        MessageEnum.REGISTRATION_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE.getMessage(),
                        projectCreateKeyboard.getKeyboard(chatId, userState)
                );
            } else {
                userStateKeeper.setState(chatId, StateEnum.WORKING_STATE.getStateName());
                messageWithoutReturn.sendMessage(
                        chatId,
                        MessageEnum.SETTINGS_TIME_ZONE_DEFINITION_COMPLETE_MESSAGE.getMessage(),
                        doneButtonKeyboard.getKeyboard(chatId, userState)
                );
            }
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
