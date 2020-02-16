package com.hari.microservices.core.recommendation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RecommendationServiceApplicationTests {

	@Autowired
	private WebTestClient client;

	@Test
	public void getrecommendationssByProductId() {

		int productId = 1;

		client.get().uri("/recommendations?productId=" + productId).accept(APPLICATION_JSON).exchange().expectStatus()
				.isOk().expectHeader().contentType(APPLICATION_JSON).expectBody().jsonPath("$.length()").isEqualTo(3)
				.jsonPath("$[0].productId").isEqualTo(productId);
	}

	@Test
	public void getrecommendationssMissingParameter() {

		client.get().uri("/recommendations").accept(APPLICATION_JSON).exchange().expectStatus().isEqualTo(BAD_REQUEST)
				.expectHeader().contentType(APPLICATION_JSON).expectBody().jsonPath("$.path")
				.isEqualTo("/recommendations").jsonPath("$.message")
				.isEqualTo("Required int parameter 'productId' is not present");
	}

	@Test
	public void getrecommendationssInvalidParameter() {

		client.get().uri("/recommendations?productId=no-integer").accept(APPLICATION_JSON).exchange().expectStatus()
				.isEqualTo(BAD_REQUEST).expectHeader().contentType(APPLICATION_JSON).expectBody().jsonPath("$.path")
				.isEqualTo("/recommendations").jsonPath("$.message").isEqualTo("Type mismatch.");
	}

	@Test
	public void getrecommendationssNotFound() {

		int productIdNotFound = 113;

		client.get().uri("/recommendations?productId=" + productIdNotFound).accept(APPLICATION_JSON).exchange()
				.expectStatus().isOk().expectHeader().contentType(APPLICATION_JSON).expectBody().jsonPath("$.length()")
				.isEqualTo(0);
	}

	@Test
	public void getrecommendationssInvalidParameterNegativeValue() {

		int productIdInvalid = -1;

		client.get().uri("/recommendations?productId=" + productIdInvalid).accept(APPLICATION_JSON).exchange()
				.expectStatus().isEqualTo(UNPROCESSABLE_ENTITY).expectHeader().contentType(APPLICATION_JSON)
				.expectBody().jsonPath("$.path").isEqualTo("/recommendations").jsonPath("$.message")
				.isEqualTo("Invalid productId: " + productIdInvalid);
	}

}
