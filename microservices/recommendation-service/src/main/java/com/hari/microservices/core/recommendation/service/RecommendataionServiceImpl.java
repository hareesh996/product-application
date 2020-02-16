package com.hari.microservices.core.recommendation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hari.microservices.util.exceptions.InvalidInputException;
import com.hari.microservices.util.http.ServiceUtil;
import com.hari.microservices.util.logger.Logger;

import microservices.core.api.recommendation.Recommendation;
import microservices.core.api.recommendation.RecommendationService;

@RestController()
public class RecommendataionServiceImpl implements RecommendationService {

	private static final Logger LOG = Logger.getInstance(RecommendataionServiceImpl.class);

	private final ServiceUtil serviceUtil;

	@Autowired
	public RecommendataionServiceImpl(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@Override
	public List<Recommendation> getRecommendations(int productId) {

		if (productId < 1)
			throw new InvalidInputException("Invalid productId: " + productId);

		if (productId == 113) {
			LOG.debug("No recommendations found for productId: {}", productId);
			return new ArrayList<>();
		}

		List<Recommendation> list = new ArrayList<>();
		list.add(new Recommendation(productId, 1, "Author 1", 1, "Content 1", serviceUtil.getServiceAddress()));
		list.add(new Recommendation(productId, 2, "Author 2", 2, "Content 2", serviceUtil.getServiceAddress()));
		list.add(new Recommendation(productId, 3, "Author 3", 3, "Content 3", serviceUtil.getServiceAddress()));

		LOG.debug("/recommendation response size: {}", list.size());

		return list;
	}

}
