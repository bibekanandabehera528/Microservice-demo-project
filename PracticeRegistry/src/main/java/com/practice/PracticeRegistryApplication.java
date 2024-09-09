package com.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PracticeRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeRegistryApplication.class, args);
	}
}
