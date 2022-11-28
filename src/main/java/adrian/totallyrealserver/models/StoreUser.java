package adrian.totallyrealserver.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class StoreUser //TODO change name and see if it fixes error
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;

	@OneToMany
	private Set<CartItem> cart = new HashSet<>();

	@OneToMany(mappedBy = "storeUser")
	private List<StoreOrder> storeOrders;


	protected StoreUser(){}

	public StoreUser(String name, String email)
	{
		this.name = name;
		this.email = email;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Set<CartItem> getCart()
	{
		return cart;
	}

	public void setCart(Set<CartItem> cart)
	{
		this.cart = cart;
	}

	public List<StoreOrder> getOrders()
	{
		return storeOrders;
	}

	public void setOrders(List<StoreOrder> storeOrders)
	{
		this.storeOrders = storeOrders;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}