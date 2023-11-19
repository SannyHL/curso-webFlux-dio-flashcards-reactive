package com.br.flashcardsreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class FlashcardsReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashcardsReactiveApplication.class, args);
	}

}
