package com.mm.farms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@OpenAPIDefinition(
		info = @Info(
				title = "Farm Service API",
				version = "1.0.0",
				description = "API documentation for Farm microservice"
		)
)
@SpringBootApplication
@EnableDiscoveryClient
public class FarmServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmServicesApplication.class, args);
	}

}
