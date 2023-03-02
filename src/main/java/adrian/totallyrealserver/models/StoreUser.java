package adrian.totallyrealserver.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class StoreUser
{
	private static final long OTP_DURATION = 300000; // five minutes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;

	private String oneTimePassword;

	private Date otpRequestTime; // when was the otp requested

	@OneToMany
	private Set<CartItem> cart = new HashSet<>();

	@OneToMany(mappedBy = "storeUser")
	private List<StoreOrder> storeOrders;

	@OneToMany(mappedBy = "user")
	private List<Review> reviews;

	protected StoreUser(){}

	public StoreUser(String name, String email)
	{
		this.name = name;
		this.email = email;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public String getOneTimePassword()
	{
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword)
	{
		this.oneTimePassword = oneTimePassword;
	}

	public Date getOtpRequestTime()
	{
		return otpRequestTime;
	}

	public void setOtpRequestTime(Date otpRequestTime)
	{
		this.otpRequestTime = otpRequestTime;
	}

	public Set<CartItem> getCart()
	{
		return cart;
	}

	public void setCart(Set<CartItem> cart)
	{
		this.cart = cart;
	}

	public List<StoreOrder> getStoreOrders()
	{
		return storeOrders;
	}

	public void setStoreOrders(List<StoreOrder> storeOrders)
	{
		this.storeOrders = storeOrders;
	}

	public List<Review> getReviews()
	{
		return reviews;
	}

	public void setReviews(List<Review> reviews)
	{
		this.reviews = reviews;
	}
}
