package io.catalyte.training.sportsproducts.domains.review;

import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.product.ProductServiceImpl;
import io.catalyte.training.sportsproducts.domains.user.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReviewValidator {
  private final Logger logger = LogManager.getLogger(ReviewValidator.class);

  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  @Autowired
  public ReviewValidator(
      ProductRepository productRepository,
      ReviewRepository reviewRepository,
      UserRepository userRepository) {
    this.productRepository = productRepository;
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
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
