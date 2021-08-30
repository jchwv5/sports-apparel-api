package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.product.ProductServiceImpl;
import io.catalyte.training.sportsproducts.domains.user.UserRepository;
import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class provides the implementation for the ReviewService interface.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

  private final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private UserRepository userRepository;

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

  /**
   * Persists a review to the database
   *
   * @param review - The review to be persisted
   * @return The persisted review with its ids
   */
  public Review saveReview(Review review) {
    try {
      validateReview(review);
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

  /**
   * Validates credit card before purchase is able to be saved
   *
   * @param review - the credit card information to validate
   */
  void validateReview(Review review) {
    ArrayList<String> errors = new ArrayList<>();

    if (review == null) {
      errors.add("No review provided");
      rejectReview(errors);
    }

    assert review != null;
    validateUserId(errors, review);
    boolean productIdIsValid = validateProductId(errors, review);
    validateRating(errors, review);
    if (productIdIsValid) {
      validateReviewDate(errors, review);
    }

    if (!errors.isEmpty()) {
      rejectReview(errors);
    }
  }

  private void validateUserId(ArrayList<String> errors, Review review) {
    try {
      Long userId = review.getUserId();
      if (userId < 1 || userId > userRepository.count()) {
        errors.add("User not found in database");
      }
    } catch (NullPointerException e) {
      errors.add("Review must have a user ID associated with it");
    }
  }


  private boolean validateProductId(ArrayList<String> errors, Review review) {
    try {
      Long productId = review.getProductId();
      if (productId < 0 || productId > productRepository.count()) {
        errors.add("Product with ID " + productId + " not found in database");
        return false;
      }
    } catch (NullPointerException e) {
      errors.add("Review must have a product ID associated with it");
      return false;
    }
    return true;
  }

  private void validateRating(ArrayList<String> errors, Review review) {
    Integer rating = review.getRating();

    if (rating < 1 || rating > 5) {
      errors.add("Product rating must be a positive integer 1 - 5");
    }
  }

  private void validateReviewDate(ArrayList<String> errors, Review review) {
    try {
      LocalDate reviewDate = review.getDate();
      Long productId = review.getProductId();
      String productReleaseDate = "";

      if (reviewDate == null) {
        errors.add("Review must have a review date");
        return;
      }

      ProductServiceImpl productService = new ProductServiceImpl(productRepository);
      productReleaseDate = productService.getProductById(productId).getReleaseDate().trim();

      LocalDate formattedReleaseDate = LocalDate.parse(productReleaseDate);

      assert formattedReleaseDate != null;
      if (reviewDate.isBefore(formattedReleaseDate)) {
        errors.add("Review cannot be before product release date");
      }
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * Validation helper method to throw exception with appropriate message
   *
   * @param errors list of error messages detailing what caused validation to fail
   */
  private void rejectReview(ArrayList<String> errors) {
    StringBuilder message = new StringBuilder();
    for (int i = 0; i < errors.size(); i++) {
      message.append(errors.get(i));
      if (i < errors.size() - 1) {
        message.append("; ");
      }
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "The review was unable to be saved - " + message);
  }
}
