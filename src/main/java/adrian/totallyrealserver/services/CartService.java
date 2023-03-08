package adrian.totallyrealserver.services;

import adrian.totallyrealserver.models.CartItem;
import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.CartItemRepository;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CartService
{
	private final StoreUserRepository storeUserRepository;
	private final CartItemRepository cartItemRepository;

	public CartService(StoreUserRepository storeUserRepository, CartItemRepository cartItemRepository)
	{
		this.storeUserRepository = storeUserRepository;
		this.cartItemRepository = cartItemRepository;
	}

	public Set<CartItem> getStoreUserCartByEmail(String email)
	{
		StoreUser storeUser = storeUserRepository.findStoreUserByEmail(email);
		return storeUser.getCart();
	}

	public void addToStoreUserCart(CartItem cartItem, String email)
	{
		StoreUser storeUser = storeUserRepository.findStoreUserByEmail(email);

		Set<CartItem> cart = storeUser.getCart();

		CartItem cartItemFromSearch = searchSet(cart, cartItem);

		if (cartItemFromSearch != null) // if cart item is found add quantity's together to avoid duplicates
		{
			cart.remove(cartItemFromSearch);
			cartItem.setId(cartItemFromSearch.getId());
			cartItem.setQuantity(cartItemFromSearch.getQuantity() + cartItem.getQuantity());
		}

		cartItemRepository.save(cartItem);

		cart.add(cartItem);

		storeUserRepository.save(storeUser);
	}

	public int getStoreUserCartQuantity(String email)
	{
		StoreUser userFromSearch = storeUserRepository.findStoreUserByEmail(email);

		int cartQuantity = 0;

		for (CartItem cartItem : userFromSearch.getCart())
		{
			cartQuantity += cartItem.getQuantity();
		}

		return cartQuantity;
	}

	private <T> T searchSet(Set<T> set, T key) // iterate over set to see if key exists
	{
		for (T item : set)
		{
			if (item.equals(key))
			{
				return item;
			}
		}

		return null;
	}

}
