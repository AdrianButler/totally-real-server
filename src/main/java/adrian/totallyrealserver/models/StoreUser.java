package adrian.totallyrealserver.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class StoreUser implements UserDetails
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;

	private String oneTimePassword;

	private Date otpRequestDate; // when was the otp requested

	@OneToMany
	private Set<CartItem> cart = new HashSet<>();

	@OneToMany(mappedBy = "storeUser")
	private List<StoreOrder> storeOrders;

	@OneToMany(mappedBy = "user")
	private List<Review> reviews;

	protected StoreUser()
	{
	}

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

	public Date getOtpRequestDate()
	{
		return otpRequestDate;
	}

	public void setOtpRequestDate(Date otpRequestDate)
	{
		this.otpRequestDate = otpRequestDate;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return Collections.emptyList();
	}

	@Override
	public String getPassword()
	{
		return oneTimePassword;
	}

	@Override
	public String getUsername()
	{
		return email;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
