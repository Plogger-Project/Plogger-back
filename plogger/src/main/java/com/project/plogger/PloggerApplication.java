package com.project.plogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class PloggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PloggerApplication.class, args);
	}

}
