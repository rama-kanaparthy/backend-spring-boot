package com.rama.backend_spring_boot.controller;

import com.rama.backend_spring_boot.model.LookupUser;
import com.rama.backend_spring_boot.service.AuthService;
import com.rama.backend_spring_boot.service.LookupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
