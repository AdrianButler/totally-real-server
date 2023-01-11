package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.Product;
import adrian.totallyrealserver.models.Review;
import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.ProductRepository;
import adrian.totallyrealserver.repositories.ReviewRepository;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController
{
	@Autowired
	StoreUserRepository storeUserRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@PostMapping("/review")
	public Review createReview(@RequestBody HashMap<String, String> requestBody)
	{
		Long userId = Long.valueOf(requestBody.get("userId"));
		Long productId = Long.valueOf(requestBody.get("productId"));

		StoreUser storeUser = storeUserRepository.findById(userId).get();
		Product product = productRepository.findById(productId).get();

		int rating = Integer.parseInt(requestBody.get("rating"));

		String reviewBody = requestBody.get("reviewBody");

		Review review = new Review(storeUser, product, rating, reviewBody);

		review = reviewRepository.save(review);

		return review;
	}
}
