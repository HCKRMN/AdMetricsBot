package com.kuzmichev.AdMetricsBot.service;

import com.kuzmichev.AdMetricsBot.model.UserRepository;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IpToTimeZone {
    private final String api_key;
    private IPGeolocationAPI api;
    @Autowired
    private UserRepository userRepository;
    public IpToTimeZone(@Value("${IPGeolocationAPI}") String api_key) {
        this.api_key = api_key;
    }
    @PostConstruct
    private void init() {
        api = new IPGeolocationAPI(api_key);
    }
    public double convertIpToTimeZone(long chatId, String ip) {
        // Get geolocation for IP address (1.1.1.1) and fields (geo, time_zone and currency)
        GeolocationParams geoParams = new GeolocationParams();
//        String ip = userRepository.findById(chatId).get().getIp();
        System.out.println(ip);
        geoParams.setIPAddress(ip);
        geoParams.setFields("geo,time_zone,currency,offset");
        Geolocation geolocation = api.getGeolocation(geoParams);

        // Проверяем подключение
        if (geolocation.getStatus() == 200) {
            double timeZone = geolocation.getTimezone().getOffset();

            return timeZone;



//            System.out.println(timeZone);
//
//            Optional<User> userOptional = userRepository.findByChatId(chatId);
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//                user.setTimeZone(timeZone);
//                userRepository.save(user);
//                System.out.println(userRepository.findByChatId(chatId).get().getTimeZone());
//            }


        } else {
            System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
        }
        return 666;
    }
}