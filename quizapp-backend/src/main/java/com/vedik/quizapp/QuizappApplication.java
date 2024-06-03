package com.vedik.quizapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class QuizappApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuizappApplication.class, args);
	}

}
