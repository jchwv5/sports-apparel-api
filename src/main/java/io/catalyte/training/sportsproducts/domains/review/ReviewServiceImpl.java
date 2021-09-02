package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.user.UserRepository;
import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * This class provides the implementation for the ReviewService interface.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

  private final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  @Autowired
  public ReviewServiceImpl(
      ProductRepository productRepository,
      ReviewRepository reviewRepository,
      UserRepository userRepository) {
    this.productRepository = productRepository;
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
  }

  /**
   * Retrieves all reviews associated with a specific product ID.
   *
   * @param id product ID used to query the database
   * @return a list of all reviews for a given product
   */
  public List<Review> getReviewsByProductId(Long id) {
    if (id == null || id.toString().trim().isEmpty()) {
      throw new ResourceNotFound("No product ID specified for request.");
    }

    try {
      if (productRepository.getProductById(id) != null) {
        return reviewRepository.getReviewsByProductId(id);
      } else {
        logger.info("Product with ID: " + id + ", does not exist.");
        throw new ResourceNotFound("Product with ID: " + id + ", does not exist.");
      }
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Retrieves all reviews associated with a specific user ID.
   *
   * @param id user ID used to query the database
   * @return a list of all reviews for a given user
   */
  public List<Review> getReviewsByUserId(Long id) {
    if (id == null || id.toString().trim().isEmpty()) {
      throw new ResourceNotFound("No user ID specified for request.");
    }

    try {
      if (userRepository.getUserById(id) != null) {
        return reviewRepository.getReviewsByUserId(id);
      } else {
        logger.info("User with ID: " + id + ", does not exist.");
        throw new ResourceNotFound("User with ID: " + id + ", does not exist.");
      }
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Persists a review to the database
   *
   * @param review the review to be persisted
   * @return the persisted review with its IDs
   */
  public Review saveReview(Review review) {
    ReviewValidator validator = new ReviewValidator(productRepository, reviewRepository,
        userRepository);
    try {
      validator.validateReview(review);
    } catch (IllegalArgumentException e) {
      logger.error(e.getMessage());
      throw new BadRequest(e.getMessage());
    }

    try {
      reviewRepository.save(review);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }

    return review;
  }
}