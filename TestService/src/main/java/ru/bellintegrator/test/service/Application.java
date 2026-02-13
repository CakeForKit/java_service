package ru.bellintegrator.test.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableCaching
@RestController
@SpringBootApplication
public class Application {
	static void main(String[] args) { SpringApplication.run(Application.class, args); }
}
