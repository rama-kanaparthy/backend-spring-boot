package com.rama.backend_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JwtValidatorService {

    @Autowired
    private RestTemplate restTemplate;

    private final String AUTH_SERVER_URL = "http://authserver:8080/validate"; // URL of the AuthServer validation endpoint

    public boolean validateJwt(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(AUTH_SERVER_URL, HttpMethod.GET, entity, Boolean.class);
            return response.getBody();
        } catch (Exception e) {
            return false;  // Invalid token or error in communication
        }
    }
}

