package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController
{
	@GetMapping("/featured-product")
	public Product getFeaturedProducts()
	{
		final String name = "4090 Founders Edition";
		final String description = "";
		final double price = 1599.99;
		final double rating = 4.8;
		final List<String> images = new ArrayList<>();
		images.add("https://i.ibb.co/d0tGkZc/4090fe.webp");

		Product product = new Product(name, description, price, rating, images);

		return product;
	}
}
