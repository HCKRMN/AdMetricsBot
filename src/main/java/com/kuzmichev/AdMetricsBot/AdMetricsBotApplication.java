package com.kuzmichev.AdMetricsBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@PropertySource(value = "file:./.env", ignoreResourceNotFound = true)
public class AdMetricsBotApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AdMetricsBotApplication.class);

		// Указываем внешний файл конфигурации
		Map<String, Object> props = new HashMap<>();
		props.put("spring.config.additional-location", "file:./.env");

		ConfigurableEnvironment environment = app.run(args).getEnvironment();
		environment.getPropertySources().addFirst(new MapPropertySource("custom", props));
	}
}
