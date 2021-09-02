package io.catalyte.training.sportsproducts.domains.review;

import static io.catalyte.training.sportsproducts.constants.Paths.REVIEWS_PATH;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes endpoint for the product reviews domain
 */
@RestController
@RequestMapping(value = REVIEWS_PATH)
public class ReviewController {

  Logger logger = LogManager.getLogger(ReviewController.class);

  @Autowired
  private ReviewService reviewService;

  @GetMapping(value = "/product")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<List<Review>> getReviewsByProductId(@RequestParam (required = false) Long id) {
    logger.info("Request received to get reviews for product id: " + id);

    return new ResponseEntity<>(reviewService.getReviewsByProductId(id), HttpStatus.OK);
  }

  @GetMapping(value = "/user")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<List<Review>> getReviewsByUserId(@RequestParam (required = false) Long id) {
    logger.info("Request received to get reviews from user id: " + id);

    return new ResponseEntity<>(reviewService.getReviewsByUserId(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity saveReview(@RequestBody Review review) {

    reviewService.saveReview(review);

    return new ResponseEntity<>(review, HttpStatus.CREATED);
  }
}
