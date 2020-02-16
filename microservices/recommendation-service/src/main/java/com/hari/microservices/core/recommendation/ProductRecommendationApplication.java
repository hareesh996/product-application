package com.hari.microservices.core.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hari")
public class ProductRecommendationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductRecommendationApplication.class, args);
	}

}
