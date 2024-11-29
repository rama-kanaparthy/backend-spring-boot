package com.rama.backend_spring_boot.controller;

import com.rama.backend_spring_boot.model.LookupUser;
import com.rama.backend_spring_boot.service.AuthService;
import com.rama.backend_spring_boot.service.LookupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/lookup")
public class LookupUserController {

    private LookupService lookupService;

    @Autowired
    public LookupUserController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @GetMapping("/users")
    public ResponseEntity<String> getLookupUsers() throws InterruptedException, ExecutionException {
        CompletableFuture<LookupUser> info1 = lookupService.findUser("Pytorch");
        CompletableFuture<LookupUser> info2 = lookupService.findUser("Tensorflow");
        CompletableFuture<LookupUser> info3 = lookupService.findUser("Scikit-learn");
        CompletableFuture<LookupUser> info4 = lookupService.findUser("spring-boot");
        CompletableFuture<LookupUser> info5 = lookupService.findUser("spring-security");
        CompletableFuture<LookupUser> info6 = lookupService.findUser("spring-mvc");
        CompletableFuture<String> info7 = lookupService.fetchData();
        lookupService.performTask();
        CompletableFuture.allOf(info1, info2, info3, info4,info5,info6,info7).join();
        log.info("------------>" + info1.get());
        log.info("------------>" + info2.get());
        log.info("------------>" + info3.get());
        log.info("------------>" + info4.get());
        log.info("------------>" + info5.get());
        log.info("------------>" + info6.get());
        log.info("------------>" + info7.get());

        return ResponseEntity.ok("ok ");
    }

    @GetMapping("/square")
    public CompletableFuture<ResponseEntity<Integer>> getSquare(@RequestParam Integer number) throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> squareFuture = lookupService.calculateSquare(number);

        return squareFuture.thenApply(result -> {
            // Log the result
            log.info("Square is: " + result);
            return ResponseEntity.ok(result); // Return HTTP 200 with the result
        });

    }

    @GetMapping("/combine")
    public CompletableFuture<ResponseEntity<Integer>> combineOperations(
            @RequestParam int num1,
            @RequestParam int num2,
            @RequestParam int num3,
            @RequestParam int num4) {

        CompletableFuture<Integer> addition = lookupService.add(num1, num2);
        CompletableFuture<Integer> multiplication = lookupService.multiply(num3, num4);

        CompletableFuture<Integer> combinedResult = addition.thenCombine(multiplication, (sum, product) -> {
            int total = sum + product;
            log.info("Combined Result: " + total);
            return total;
        });

        return combinedResult.thenApply(ResponseEntity::ok);
    }

    @GetMapping("/fetchAll")
    public CompletableFuture<ResponseEntity<Map<String, String>>> fetchAllData() {
        CompletableFuture<String> userFuture = lookupService.fetchUserData();
        CompletableFuture<String> productFuture = lookupService.fetchProductData();
        CompletableFuture<String> orderFuture = lookupService.fetchOrderData();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(userFuture, productFuture, orderFuture);

        return allFutures.thenApply(voidResult -> {
            Map<String, String> response = new HashMap<>();
            try {
                response.put("User", userFuture.get());
                response.put("Product", productFuture.get());
                response.put("Order", orderFuture.get());
            } catch (Exception e) {
                throw new RuntimeException("Error fetching data", e);
            }
            return ResponseEntity.ok(response);
        });
    }

}
