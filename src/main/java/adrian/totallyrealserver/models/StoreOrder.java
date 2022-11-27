package adrian.totallyrealserver.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;

@Entity
public class StoreOrder
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date date;
	private String trackingNumber;

	@ManyToOne
	private StoreUser storeUser;

	@OneToMany
	private List<Product> items;

	protected StoreOrder() {}

	public StoreOrder(StoreUser storeUser, List<Product> items)
	{
		this.storeUser = storeUser;
		this.items = items;

		date = new Date();
		trackingNumber = "A5GH734HDS82347H";
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getTrackingNumber()
	{
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber)
	{
		this.trackingNumber = trackingNumber;
	}

	public StoreUser getUser()
	{
		return storeUser;
	}

	public void setUser(StoreUser storeUser)
	{
		this.storeUser = storeUser;
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