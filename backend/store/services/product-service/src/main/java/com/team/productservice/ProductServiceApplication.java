package com.team.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.team.jwtspringbootstarter.jwt.config")
public class ProductServiceApplication{
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
}
