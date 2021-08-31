package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository
    extends JpaRepository<Review, Long> {

  List<Review> getReviewsByProductId(Long id);

}
