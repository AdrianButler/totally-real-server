package adrian.totallyrealserver.repositories;

import adrian.totallyrealserver.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
}