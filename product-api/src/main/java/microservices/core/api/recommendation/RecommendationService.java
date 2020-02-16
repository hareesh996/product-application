package microservices.core.api.recommendation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RecommendationService {

	@GetMapping(value = "/recommendations", produces = "application/json")
	public List<Recommendation> getRecommendations(@RequestParam("productId") int productId);

}
