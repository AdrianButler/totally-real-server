package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.dtos.cart.AddToCartRequest;
import adrian.totallyrealserver.models.CartItem;
import adrian.totallyrealserver.models.Product;
import adrian.totallyrealserver.repositories.ProductRepository;
import adrian.totallyrealserver.services.CartService;
import java.security.Principal;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cart")
public class CartController
{
	private final CartService cartService;
	private final ProductRepository productRepository;

	public CartController(CartService cartService, ProductRepository productRepository)
	{
		this.cartService = cartService;
		this.productRepository = productRepository;
	}

	@GetMapping
	public Set<CartItem> getUserCart(Principal principal)
	{
		return cartService.getStoreUserCartByEmail(principal.getName());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addToCart(AddToCartRequest addToCartRequest, Principal principal)
	{
		long productId = addToCartRequest.getProductId();
		int quantity = addToCartRequest.getQuantity();

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with given ID not found"));

		CartItem cartItem = new CartItem(quantity, product);

		cartService.addToStoreUserCart(cartItem, principal.getName());
	}

	@DeleteMapping("/cart")
	public void removeFromCart(@RequestBody long productId, Principal principal)
	{
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with given ID not found"));

		cartService.deleteProductFromUserStoreCart(product, principal.getName());
	}

	@GetMapping("/cart/quantity")
	public int getUserCartQuantity(Principal principal)
	{
		return cartService.getStoreUserCartQuantity(principal.getName());
	}

}
