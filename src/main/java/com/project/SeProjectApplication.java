package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EntityScan("com.project.entities")
@EnableMongoRepositories("com.project.repos")
@ComponentScan(basePackages = { "com" })
public class SeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeProjectApplication.class, args);
	}

}
