package adrian.totallyrealserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Review
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@ManyToOne
	@JsonIgnore
	private StoreUser user;

	@ManyToOne
	@JsonIgnore
	private Product product;

	@Column(columnDefinition = "TEXT", length = 1000)
	private String reviewBody;

	private int rating;
	private String author;
	private Date dateReviewed;

	protected Review()
	{
	}

	public Review(StoreUser user, Product product, int rating, String reviewBody)
	{
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.reviewBody = reviewBody;
		this.author = user.getName();
		this.dateReviewed = new Date();
	}

	public StoreUser getUser()
	{
		return user;
	}

	public void setUser(StoreUser user)
	{
		this.user = user;
	}

	public int getRating()
	{
		return rating;
	}

	public void setRating(int rating)
	{
		this.rating = rating;
	}

	public String getReviewBody()
	{
		return reviewBody;
	}

	public void setReviewBody(String reviewBody)
	{
		this.reviewBody = reviewBody;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public Date getDateReviewed()
	{
		return dateReviewed;
	}

	public void setDateReviewed(Date dateReviewed)
	{
		this.dateReviewed = dateReviewed;
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
