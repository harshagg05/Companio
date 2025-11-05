package com.companio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CompanioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanioApplication.class, args);
	}

}
