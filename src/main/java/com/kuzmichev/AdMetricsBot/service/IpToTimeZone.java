package com.kuzmichev.AdMetricsBot.service;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IpToTimeZone {
    private final String api_key;
    private IPGeolocationAPI api;
    public IpToTimeZone(@Value("${IPGeolocationAPI}") String api_key) {
        this.api_key = api_key;
    }
    @PostConstruct                                                                          // Зачем тут это??? ВСЕ ПЕРЕДЕЛАТЬ
    private void init() {
        api = new IPGeolocationAPI(api_key);
    }
    public int convertIpToTimeZone(String ip) {
        GeolocationParams geoParams = new GeolocationParams();
        geoParams.setIPAddress(ip);
        geoParams.setFields("geo,time_zone,currency,offset");
        Geolocation geolocation = api.getGeolocation(geoParams);

        if (geolocation.getStatus() == 200) {
            double timeZone = geolocation.getTimezone().getOffset();
            // timeZone - представляет собой временную зону в часах от гринвича
            // переводим его в минуты и вычитаем разницу с мск, так как сервер с там
            return (int) (timeZone * 60 - 180);


        } else {
            System.out.printf("Ошибка получения временной зоны: %d, Пояснение: %s\n", geolocation.getStatus(), geolocation.getMessage());
        }
        return 666;
    }
}