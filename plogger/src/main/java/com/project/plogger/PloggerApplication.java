package com.project.plogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan
@EnableScheduling
public class PloggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PloggerApplication.class, args);
	}

}
