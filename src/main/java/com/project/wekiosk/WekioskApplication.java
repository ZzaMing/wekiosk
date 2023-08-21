package com.project.wekiosk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WekioskApplication {

	public static void main(String[] args) {
		SpringApplication.run(WekioskApplication.class, args);
	}

}
