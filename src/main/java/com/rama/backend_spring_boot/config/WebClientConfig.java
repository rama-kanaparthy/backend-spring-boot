package com.rama.backend_spring_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.github.com") // Set base URL if common
                .defaultHeader("User-Agent", "Spring-Boot-App") // Add default headers if required
                .build();
    }
}

