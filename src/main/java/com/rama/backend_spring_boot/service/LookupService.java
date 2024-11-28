package com.rama.backend_spring_boot.service;

import com.rama.backend_spring_boot.model.LookupUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Data
@Slf4j
@RequiredArgsConstructor
@Service
public class LookupService {
    private static final String GITHUB_USERS_URL = "https://api.github.com/users/%s";
    private final RestTemplate restTemplate;

    public CompletableFuture<LookupUser> findUser(String user) throws InterruptedException {
        log.info("Looking up  " + user);
        String url = String.format(GITHUB_USERS_URL, user);
        LookupUser result = restTemplate.getForObject(url, LookupUser.class);
       // Thread.sleep(4000L);
        return CompletableFuture.completedFuture(result);
    }
}
