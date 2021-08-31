package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * This interface provides an abstraction layer for the Reviews Service
 */
@Service
public interface ReviewService {

  List<Review> getAllReviews(Review review);

  List<Review> getReviewsByProductId(Long id);

  List<Review> getReviewsByUserId(Long id);

  Review saveReview(Review review);
}
