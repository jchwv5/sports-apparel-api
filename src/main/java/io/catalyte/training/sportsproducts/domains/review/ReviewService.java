package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;

/**
 * This interface provides an abstraction layer for the Reviews Service
 */
public interface ReviewService {

  List<Review> getAllReviews(Review review);

//  List<Review> getReviewsByProductId(Long id);
//
//  List<Review> getReviewsByProductName(String productName);

  Review saveReview(Review review);
}
