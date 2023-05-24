package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.Product;
import adrian.totallyrealserver.repositories.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController
{
	private final ProductRepository productRepository;

	public ProductController(ProductRepository productRepository)
	{
		this.productRepository = productRepository;
	}

	@GetMapping("/featured")
	public Product getFeaturedProducts()
	{
		return productRepository.findById(1L).get();
	}

	@GetMapping("/{id}")
	public Product getProduct(@PathVariable Long id)
	{
		return productRepository.findById(id).get();
	}

	@PostMapping("/seed")
	public void seedProduct(@RequestBody Product product)
	{
		productRepository.save(product);
	}
}
