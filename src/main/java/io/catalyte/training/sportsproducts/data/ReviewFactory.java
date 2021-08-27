package io.catalyte.training.sportsproducts.data;


import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.review.Review;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class provides tools for random generation of user product reviews.
 */
public class ReviewFactory {

  /**
   * Returns a random product rating.
   *
   * @return - a product rating int
   */
  public static Long getUserId() {

    return ;
  }

  /**
   * Returns a random product rating.
   *
   * @return - a product rating int
   */
  public static Long getProductId(int id) {

    return ;
  }

  /**
   * Returns a random product rating.
   *
   * @return - a product rating int
   */
  public static Integer getRating() {
    Random randomGenerator = new Random();
    return randomGenerator.nextInt(5) + 1;
  }

  public List<Review> generateRandomReviews(Integer maxReviewsPerProduct,
      ProductRepository productRepository) {

    Random randomGenerator = new Random();
    List<Review> reviewList = new ArrayList<>();
    int reviewsPerProduct;
    Long productRepositoryLength = productRepository.count();

    for (int i = 0; i < productRepositoryLength; i++) {
      reviewsPerProduct = randomGenerator.nextInt(maxReviewsPerProduct);
      for (int j = 0; j < reviewsPerProduct; j++) {
        reviewList.add(createRandomReview(i));
      }
    }

    return reviewList;
  }

  public Review createRandomReview(Long id) {
    Review review = new Review();

    Long productId = id;
    Long userId = ReviewFactory.getUserId();
    Integer rating = ReviewFactory.getRating();

    review.setRating(rating);

    return review;
  }
}
