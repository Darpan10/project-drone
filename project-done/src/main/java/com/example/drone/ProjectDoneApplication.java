package com.example.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectDoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectDoneApplication.class, args);
	}

}
