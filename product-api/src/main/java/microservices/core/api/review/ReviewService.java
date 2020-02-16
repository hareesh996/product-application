package microservices.core.api.review;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewService {

	@GetMapping(value = "reviews", produces = "application/json")
	public List<Review> getReviews(@RequestParam("productId") int productId);

}
