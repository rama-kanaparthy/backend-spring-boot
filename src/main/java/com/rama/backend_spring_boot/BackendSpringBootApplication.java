package com.rama.backend_spring_boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Slf4j
@EntityScan(basePackages = "com.rama.backend_spring_boot.model")
@SpringBootApplication
public class BackendSpringBootApplication {

	public static void main(String[] args) {
		log.info("Starting the application...");
		SpringApplication.run(BackendSpringBootApplication.class, args);
	}

}
