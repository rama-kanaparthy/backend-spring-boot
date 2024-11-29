package com.rama.backend_spring_boot.service;

import com.rama.backend_spring_boot.model.LookupUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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

    @Async("customExecutor")
    public CompletableFuture<LookupUser> findUser(String user) throws InterruptedException {
        log.info("Looking up  " + user);
        String url = String.format(GITHUB_USERS_URL, user);
        LookupUser result = restTemplate.getForObject(url, LookupUser.class);
       // Thread.sleep(4000L);
        return CompletableFuture.completedFuture(result);
    }

    @Async("customExecutor")
    public void performTask() {
        // Long-running logic here
        log.info("Executing task in thread: " + Thread.currentThread().getName());
    }

    @Async("customExecutor")
    public CompletableFuture<String> fetchData() {
        // Simulate a long-running task
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); // Simulating delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Data fetched successfully";
        });
    }

    @Async
    public CompletableFuture<Integer> calculateSquare(int number) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Calculating square for: " + number);
            return number * number;
        });
    }

    @Async
    public CompletableFuture<Integer> add(int a, int b) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Adding " + a + " and " + b);
            return a + b;
        });
    }

    @Async
    public CompletableFuture<Integer> multiply(int a, int b) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Multiplying " + a + " and " + b);
            return a * b;
        });
    }

    @Async
    public CompletableFuture<String> fetchUserData() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching User Data...");
            return "User Data";
        });
    }

    @Async
    public CompletableFuture<String> fetchProductData() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching Product Data...");
            return "Product Data";
        });
    }

    @Async
    public CompletableFuture<String> fetchOrderData() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching Order Data...");
            return "Order Data";
        });
    }

}
