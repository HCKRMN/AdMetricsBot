package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.model.TempDataRepository;
import com.kuzmichev.AdMetricsBot.model.User;
import com.kuzmichev.AdMetricsBot.model.UserRepository;
import com.kuzmichev.AdMetricsBot.service.IpToTimeZone;
import com.kuzmichev.AdMetricsBot.telegram.utils.Messages.MessageManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Slf4j
public class UserIPController {


    // ПОЧЕМУ ТУТ АВТОВАЙРЕД????
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TempDataRepository tempDataRepository;
    @Autowired
    private MessageManagementService messageManagementService;
    @Autowired
    private IpToTimeZone ipToTimeZone;

    @GetMapping("/getip")
    public String myEndpoint(HttpServletRequest request,
                             @RequestParam(name = "chatId") String chatId) {
        String ip = getIpAddress(request);
        double timeZone = ipToTimeZone.convertIpToTimeZone(ip);

        // ЧТО ЭТО ТАКОЕ????
//        int messageId = tempDataRepository.findLastMessageIdByChatId(chatId);
//        messageManagementService.putMessageToQueue(chatId, messageId);

        Optional<User> userOptional = userRepository.findByChatId(chatId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIp(ip);
            user.setTimeZone(timeZone);
            userRepository.save(user);
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
