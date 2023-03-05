package adrian.totallyrealserver.repositories;

import adrian.totallyrealserver.models.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreUserRepository extends JpaRepository<StoreUser, Long>
{
	boolean existsByEmail(String email);
	public StoreUser findStoreUserByEmail(String email);
}
