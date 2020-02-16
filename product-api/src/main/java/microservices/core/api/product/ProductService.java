package microservices.core.api.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService {

	@GetMapping(value = "/product/{productId}", produces = "application/json")
	public Product getProduct(@PathVariable("productId") int productId);

}
