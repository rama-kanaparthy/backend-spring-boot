package com.rama.backend_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.rama.backend_spring_boot.model")
@SpringBootApplication
public class BackendSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSpringBootApplication.class, args);
	}

}
