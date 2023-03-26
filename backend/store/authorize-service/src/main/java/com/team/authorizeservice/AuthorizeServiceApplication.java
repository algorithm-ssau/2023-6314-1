package com.team.authorizeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthorizeServiceApplication.class, args);
	}
}
