package io.catalyte.training.sportsproducts.domains.review;

import static io.catalyte.training.sportsproducts.constants.Paths.REVIEWS_PATH;

import io.catalyte.training.sportsproducts.domains.purchase.PurchaseService;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes endpoint for product reviews
 */
@RestController
@RequestMapping(value = REVIEWS_PATH)
public class ReviewController {

  Logger logger = LogManager.getLogger(ReviewController.class);

  private PurchaseService purchaseService;

  @Autowired
  public ReviewController(PurchaseService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping
  public ResponseEntity saveReview(@RequestBody Review review) {

    reviewService.saveReview(review);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping
  public ResponseEntity findReviewByProuctName(
      @RequestParam(required = false) String email) {
    return new ResponseEntity<>(purchaseService.findPurchasesByEmail(email), HttpStatus.OK);
  }
}
