package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * This class provides the implementation for the ReviewService interface.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

  private final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

  ReviewRepository reviewRepository;

  @Autowired
  public ReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  /**
   * Retrieves all reviews from the database, optionally making use of an example if it is passed.
   *
   * @param review - an example review to use for querying
   * @return - a list of all reviews matching the example, or all reviews if no example was passed
   */
  public List<Review> getAllReviews(Review review) {
    try {
      return reviewRepository.findAll(Example.of(review));
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  public List<Review> getReviewsByProductId(Long id) {
    List<Review> review;

    try {
      review = reviewRepository.getReviewsByProductId(id);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }

    if (review != null) {
      return review;
    } else {
      logger.info("Get by id failed, it does not exist in the database: " + id);
      throw new ResourceNotFound("Get by id failed, it does not exist in the database: " + id);
    }
  }
//
//  public List<Review> getReviewsByProductName(String productName) {
//    List<Review> review;
//
//    try {
//      review = reviewRepository.getReviewsByProductName(productName);
//    } catch (DataAccessException e) {
//      logger.error(e.getMessage());
//      throw new ServerError(e.getMessage());
//    }
//
//    if (review != null) {
//      return review;
//    } else {
//      logger.info("Get review by product name failed; product does not exist in the database: "
//          + productName);
//      throw new ResourceNotFound(
//          "Get review by product name failed; product does not exist in the database: "
//              + productName);
//    }
//  }
//
  public Review saveReview(Review newReview) {
    try {
      reviewRepository.save(newReview);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }

    return newReview;
  }
}
