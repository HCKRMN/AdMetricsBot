package com.kuzmichev.AdMetricsBot.service;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Service
public class IpToTimeZone {
    private final String api_key;
    private IPGeolocationAPI api;
    @Value("${serverIP}")
    private String serverIP;

    public IpToTimeZone(@Value("${IPGeolocationAPI}") String api_key) {
        this.api_key = api_key;
    }
    @PostConstruct
    private void init() {
        api = new IPGeolocationAPI(api_key);
    }
    public int convertIpToTimeZone(String ip) throws UnknownHostException {
        double serverTimeZone;
        double userTimeZone;

        // Определяем временную зону сервера
        GeolocationParams ServerGeoParams = new GeolocationParams();
        ServerGeoParams.setIPAddress(serverIP);
        ServerGeoParams.setFields("geo,time_zone,currency,offset");
        Geolocation serverGeolocation = api.getGeolocation(ServerGeoParams);
        if (serverGeolocation.getStatus() == 200) {
            serverTimeZone = serverGeolocation.getTimezone().getOffset();
        } else {
            log.error("Ошибка получения временной зоны сервера: {}, Пояснение: {}", serverGeolocation.getStatus(), serverGeolocation.getMessage());
            return 0;
        }

        // Определяем временную зону пользователя
        GeolocationParams UserGeoParams = new GeolocationParams();
        UserGeoParams.setIPAddress(ip);
        UserGeoParams.setFields("geo,time_zone,currency,offset");
        Geolocation userGeolocation = api.getGeolocation(UserGeoParams);

        if (userGeolocation.getStatus() == 200) {
            userTimeZone = userGeolocation.getTimezone().getOffset();
        } else {
            log.error("Ошибка получения временной зоны пользователя: {}, Пояснение: {}", userGeolocation.getStatus(), userGeolocation.getMessage());
            return 0;
        }
        return (int) (userTimeZone - serverTimeZone) * 60;
    }
}