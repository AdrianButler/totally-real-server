package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.Product;
import adrian.totallyrealserver.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
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

	@GetMapping("/seed")
	public void seedProduct()
	{
		String name = "";
		String description = "";
		double price = 0;
		double rating = 0;

		List<String> images = new ArrayList<>();
		images.add("");

		Product product = new Product(name, description, price, rating, images);

		productRepository.save(product);
	}
}
