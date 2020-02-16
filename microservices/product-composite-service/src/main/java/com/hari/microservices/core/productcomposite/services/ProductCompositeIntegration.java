package com.hari.microservices.core.productcomposite.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import static org.springframework.http.HttpMethod.GET;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hari.microservices.util.Util;
import com.hari.microservices.util.exceptions.InvalidInputException;
import com.hari.microservices.util.exceptions.NotFoundException;
import com.hari.microservices.util.http.HttpErrorInfo;
import com.hari.microservices.util.logger.Logger;

import microservices.core.api.product.Product;
import microservices.core.api.product.ProductService;
import microservices.core.api.recommendation.Recommendation;
import microservices.core.api.recommendation.RecommendationService;
import microservices.core.api.review.Review;
import microservices.core.api.review.ReviewService;

@Component
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {

	private static final Logger LOG = Logger.getInstance(ProductCompositeIntegration.class);

	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;

	private final String productServiceUrl;
	private final String recommendationServiceUrl;
	private final String reviewServiceUrl;

	@Autowired
	public ProductCompositeIntegration(RestTemplate restTemplate, ObjectMapper mapper,

			@Value("${app.product-service.host}") String productServiceHost,
			@Value("${app.product-service.port}") int productServicePort,

			@Value("${app.recommendation-service.host}") String recommendationServiceHost,
			@Value("${app.recommendation-service.port}") int recommendationServicePort,

			@Value("${app.review-service.host}") String reviewServiceHost,
			@Value("${app.review-service.port}") int reviewServicePort) {

		this.restTemplate = restTemplate;
		this.mapper = mapper;

		productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product/";
		recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort
				+ "/recommendation?productId=";
		reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review?productId=";
	}

	public List<Recommendation> getRecommendations(int productId) {

        try {
            String url = recommendationServiceUrl + productId;

            LOG.debug("Will call getRecommendations API on URL: {}", url);
            List<Recommendation> recommendations = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {}).getBody();

            LOG.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
            return recommendations;

        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Review> getReviews(int productId) {

        try {
            String url = reviewServiceUrl + productId;

            LOG.debug("Will call getReviews API on URL: {}", url);
            
            /**
             * Generics don't hold any type of information at runtime, we can't specify that the methods expect a generic list in their responses. Instead, we can use a helper class
             * from the Spring Framework, ParameterizedTypeReference, that is designed to resolve this problem by holding the type information at runtime. This means that 
             * RestTemplate can figure out what class to map the JSON responses to. 
             */
            List<Review> reviews = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Review>>() {}).getBody();

            LOG.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
            return reviews;

        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

	@Override
	public Product getProduct(int productId) {
		String productAPIURL = Util.concat(this.productServiceUrl, "/", productId);
		try {
			Product product = this.restTemplate.getForObject(productAPIURL, Product.class);
			LOG.debug("Found a product with id: {}", product.getProductId());
			return product;
		} catch (HttpClientErrorException ex) {
			switch (ex.getStatusCode()) {

			case NOT_FOUND:
				throw new NotFoundException(getErrorMessage(ex));

			case UNPROCESSABLE_ENTITY:
				throw new InvalidInputException(getErrorMessage(ex));

			default:
				LOG.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
				LOG.warn("Error body: {}", ex.getResponseBodyAsString());
				throw ex;
			}
		}
	}
	
	private String getErrorMessage(HttpClientErrorException ex) {
		try {
			return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
		} catch (IOException ioex) {
			return ex.getMessage();
		}
	}

}