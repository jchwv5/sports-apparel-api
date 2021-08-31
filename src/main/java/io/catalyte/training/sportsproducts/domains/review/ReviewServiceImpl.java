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

  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  @Autowired
  public ReviewServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository,
      UserRepository userRepository) {
    this.productRepository = productRepository;
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
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

  /**
   * Retrieves all reviews associated with a specific product ID.
   *
   * @param id - product ID used to query the database
   * @return - a list of all reviews for a given product
   */
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
      logger.info("Get review by product ID: " + id + " failed, it does not exist in the database");
      throw new ResourceNotFound(
          "Get review by product ID: " + id + " failed, it does not exist in the database");
    }
  }

  /**
   * Retrieves all reviews associated with a specific product ID.
   *
   * @param id - product ID used to query the database
   * @return - a list of all reviews for a given product
   */
  public List<Review> getReviewsByUserId(Long id) {
    List<Review> review;

    try {
      review = reviewRepository.getReviewsByUserId(id);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }

    if (review != null) {
      return review;
    } else {
      logger.info("Get review by user ID: " + id + " failed, it does not exist in the database");
      throw new ResourceNotFound(
          "Get review by user ID: " + id + " failed, it does not exist in the database");
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
   * Validates review fields before it is saved
   *
   * @param review - the review to validate
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
    validateTitle(errors, review);

    if (!errors.isEmpty()) {
      rejectReview(errors);
    }
  }

  /**
   * Validates a user ID is provided and that it exists in the database.
   *
   * @param errors - running list of errors
   * @param review - review being verified
   */
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

  /**
   * Validates a product ID is provided and that it exists in the database.
   *
   * @param errors - running list of errors
   * @param review - review being verified
   * @return - boolean if the product ID is valid or not
   */
  private boolean validateProductId(ArrayList<String> errors, Review review) {
    try {
      Long productId = review.getProductId();
      if (productId < 0 || productId > productRepository.count()) {
        errors.add("Product with ID " + productId + " not found in database");
        return false;
      }
    } catch (NullPointerException e) {
      logger.error(e.getMessage());
      errors.add("Review must have a product ID associated with it");
      return false;
    }
    return true;
  }

  /**
   * Validates a rating is provided and that it is a positive integer between 1 and 5.
   *
   * @param errors - running list of errors
   * @param review - review being verified
   */
  private void validateRating(ArrayList<String> errors, Review review) {
    Integer rating = review.getRating();

    if (rating < 1 || rating > 5) {
      errors.add("Product rating must be a positive integer 1 - 5");
    }
  }

  /**
   * Validates the review has a date, and that the date is between the product release and today's
   * date. This method requires the review having a valid product ID associated with it.
   *
   * @param errors - running list of errors
   * @param review - review being verified
   */
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
      logger.error(e.getMessage());
    }
  }

  private void validateTitle(ArrayList<String> errors, Review review) {
    int titleLength = review.getTitle().length();

    if (titleLength > 255) {
      errors.add("Review title character length exceeded (max 255 characters)");
    }
  }

  /**
   * Validation helper method to throw exception with appropriate message
   *
   * @param errors - running list of error messages detailing what caused validation to fail
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
