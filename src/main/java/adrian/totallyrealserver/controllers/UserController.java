package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.CartItem;
import adrian.totallyrealserver.models.Product;
import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.CartItemRepository;
import adrian.totallyrealserver.repositories.ProductRepository;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import adrian.totallyrealserver.services.SetUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
	final StoreUserRepository storeUserRepository;

	final ProductRepository productRepository;

	final CartItemRepository cartItemRepository;

	final SetUtils setUtils;

	public UserController(StoreUserRepository storeUserRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, SetUtils setUtils)
	{
		this.storeUserRepository = storeUserRepository;
		this.productRepository = productRepository;
		this.cartItemRepository = cartItemRepository;
		this.setUtils = setUtils;
	}

	@PostMapping("/user/cart")
	public Set<CartItem> getUserCart(@RequestBody StoreUser user) //TODO instead of using id use jwt
	{
		StoreUser userFromSearch = storeUserRepository.findById(user.getId()).get();
		return userFromSearch.getCart();
	}

	@PostMapping("/user/cart-quantity")
	public int getUserCartQuantity(@RequestBody StoreUser user) //TODO instead of using id use jwt
	{
		StoreUser userFromSearch = storeUserRepository.findById(user.getId()).get();

		int cartQuantity = 0;

		for (CartItem cartItem : userFromSearch.getCart())
		{
			cartQuantity += cartItem.getQuantity();
		}

		return cartQuantity;
	}

	@PutMapping("/cart")
	public boolean addToCart(@RequestBody Map<String, Long> requestBody) //requestBody should be ids and quantity
	{
		long productId = requestBody.get("productId");
		long userId = requestBody.get("userId");
		int quantity = Math.toIntExact(requestBody.get("quantity"));

		Product product = productRepository.findById(productId).get();
		StoreUser user = storeUserRepository.findById(userId).get();

		Set<CartItem> cart = user.getCart();
		CartItem cartItem = new CartItem(quantity, product);

		CartItem cartItemFromSearch = setUtils.searchSet(cart, cartItem);

		if (cartItemFromSearch != null) // if cart item is found add 1 to quantity and overwrite old cartItem
		{
			cart.remove(cartItemFromSearch);
			cartItem.setId(cartItemFromSearch.getId());
			cartItem.setQuantity(cartItemFromSearch.getQuantity() + quantity);
		}

		cartItemRepository.save(cartItem);

		cart.add(cartItem);

		storeUserRepository.save(user);

		return true;
	}

	@DeleteMapping("/cart") //TODO create this (copy method above and modify)
	public boolean removeFromCart(@RequestBody HashMap<String, Long> requestBody) // requestBody should be ids
	{
		return true;
	}





}
