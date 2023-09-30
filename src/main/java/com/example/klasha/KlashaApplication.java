package com.example.klasha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KlashaApplication {
	public static void main(String[] args) {
		SpringApplication.run(KlashaApplication.class, args);
	}
}
