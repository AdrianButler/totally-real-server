package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.CartItem;
import adrian.totallyrealserver.models.Product;
import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.CartItemRepository;
import adrian.totallyrealserver.repositories.ProductRepository;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import adrian.totallyrealserver.services.SetUtils;
import java.util.HashMap;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
	@Autowired
	StoreUserRepository storeUserRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	SetUtils setUtils;

	@PutMapping("/user")
	public StoreUser createUser(@RequestBody StoreUser user)
	{
		StoreUser userFromSearch = storeUserRepository.findStoreUserByEmail(user.getEmail());

		if (userFromSearch != null) // if user already exists return that user
		{
			return userFromSearch;
		}

		StoreUser newUser = new StoreUser(user.getName(), user.getEmail());
		storeUserRepository.save(newUser);

		return newUser;
	}

	@PutMapping("/cart")
	public boolean addToCart(@RequestBody HashMap<String, Long> requestBody) //requestBody should be ids
	{


		long productId = requestBody.get("productId");
		long userId = requestBody.get("userId");

		Product product = productRepository.findById(productId).get();
		StoreUser user = storeUserRepository.findById(userId).get();

		Set<CartItem> cart = user.getCart();
		CartItem cartItem = new CartItem(1, product);

		CartItem cartItemFromSearch = setUtils.searchSet(cart, cartItem);

		if (cartItemFromSearch != null) // if cart item is found add 1 to quantity and overwrite old cartItem
		{
			cart.remove(cartItemFromSearch);
			cartItem.setId(cartItemFromSearch.getId());
			cartItem.setQuantity(cartItemFromSearch.getQuantity() + 1);
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
