package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.Product;
import adrian.totallyrealserver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController
{
	@Autowired
	ProductRepository productRepository;

	@GetMapping("/featured-product")
	public Product getFeaturedProducts()
	{
		return productRepository.findById(1L).get();
	}

	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable Long id)
	{
		return productRepository.findById(id).get();
	}
}
