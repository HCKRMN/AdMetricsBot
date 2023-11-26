package com.kuzmichev.AdMetricsBot.controllers;

import com.kuzmichev.AdMetricsBot.model.Yclients;
import com.kuzmichev.AdMetricsBot.model.YclientsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class YclientsController {
    final YclientsRepository yclientsRepository;

    @Value("${bearer-authentication}")
    String bearerAuthentication;
    @Value("${yclientsApplicationId}")
    String applicationId;
    @Value("${yclientsPartnerCallbackURL}")
    String yclientsPartnerCallbackURL;

    @RequestMapping(value = "/yclients")
    public String getYclientsToken(@RequestParam(name = "salon_id") String salonId,
                                   @RequestParam(name = "user_data") String userData,
                                   @RequestParam(name = "user_data_sign") String userDataSign,
                                   Model model) {

        // Декодирование данных пользователя
        String decodedUserData = new String(Base64.getDecoder().decode(userData), StandardCharsets.UTF_8);

        // Проверка подписи данных пользователя
        String computedSign = new HmacUtils("HmacSHA256", bearerAuthentication).hmacHex(decodedUserData);
        if (!computedSign.equals(userDataSign)) {
            // Сообщение с ошибкой если подпись не совпадает
            return "yclientsError";
        }

        // Создание HTTP-запроса
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + bearerAuthentication);
        headers.add("Accept", "application/vnd.yclients.v2+json");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("salon_id", salonId);
        requestBody.add("application_id", applicationId);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Отправка HTTP-запроса и обработка успешного ответа
            restTemplate.exchange(yclientsPartnerCallbackURL, HttpMethod.POST, requestEntity, String.class);

            // Извлечение необходимых данных из JSON
            JSONObject json = new JSONObject(decodedUserData);
            String salonName = json.getString("salon_name");

            // Генерируем UUID и переводим в текст
            String yclientsId = UUID.randomUUID().toString();
            model.addAttribute("yclientsId", yclientsId);

            Yclients yclients = Yclients.builder()
                    .yclientsId(yclientsId)
                    .salonId(salonId)
                    .salonName(salonName)
                    .build();
            yclientsRepository.save(yclients);

            return "yclients";

        } catch (HttpClientErrorException e) {
            // Обработка ошибок HTTP
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();

            model.addAttribute("error", 403);
            log.error("Произошла ошибка запроса при подключении Yclients, {}: {}", statusCode, e.getMessage());

        } catch (RestClientException e) {
            // Обработка других ошибок, не связанных с HTTP
            model.addAttribute("error", 0);
            log.error("Произошла ошибка при подключении Yclients: {}", e.getMessage());
        }
        return "yclientsError";
    }
}