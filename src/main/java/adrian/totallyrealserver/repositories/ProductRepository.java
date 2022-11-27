package adrian.totallyrealserver.repositories;

import adrian.totallyrealserver.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>
{
	public Product getProductsByName(String name);
}