package microservices.core.api.product;

public class Product {
	private int productId;
	private String name;
	private String serviceAddress;
	private int weight;

	public Product() {
		this.productId = -1;
		this.name = null;
		this.serviceAddress = null;
		this.weight = -1;
	}

	public Product(int productId, String name, int weight,  String serviceAddress) {
		super();
		this.productId = productId;
		this.name = name;
		this.serviceAddress = serviceAddress;
		this.weight = weight;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
