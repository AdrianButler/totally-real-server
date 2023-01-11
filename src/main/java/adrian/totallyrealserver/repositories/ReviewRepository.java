package adrian.totallyrealserver.repositories;

import adrian.totallyrealserver.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>
{
}
