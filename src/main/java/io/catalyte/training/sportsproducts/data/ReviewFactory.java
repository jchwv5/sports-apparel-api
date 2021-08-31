package io.catalyte.training.sportsproducts.data;


import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.review.Review;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class provides tools for random generation of user product reviews.
 */
public class ReviewFactory {

  private static final String[] subjects = {
      "I",
      "My daughter",
      "My wife",
      "My husband",
      "My son"
  };

  private static final String[] verbs = {
      "bought",
      "played with",
      "wore",
      "threw",
      "handled",
      "tried to take",
      "built",
      "wanted"
  };

  private static final String[] references = {
      "this",
      "it",
      "these"
  };

  private static final String[] positiveAdjectives = {
      "brilliant",
      "comfortable",
      "excellent",
      "exceptional",
      "great",
      "fantastic",
      "neat",
      "slick",
      "smashing",
      "wonderful"
  };

  private static final String[] negativeAdjectives = {
      "appalling",
      "atrocious",
      "awful",
      "broken",
      "disappointing",
      "horrible",
      "inadequate",
      "lousy",
      "poor",
      "terrible",
      "unacceptable"
  };

  private static final String[] neutralAdjectives = {
      " alright",
      " just ok",
      " consistent",
      " reasonable",
      " fine",
      " satisfactory",
      " passable"
  };

  /**
   * Returns a random product rating.
   *
   * @return - a product rating int
   */
  public static Long getUserId(int userRepositoryLength) {
    Random randomGenerator = new Random();
    return (long) randomGenerator.nextInt(userRepositoryLength) + 1;
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

  public static String getTitle() {
    StringBuilder title = new StringBuilder();


    return title.toString();
  };

  public static String getComment(Integer rating) {
    StringBuilder comment = new StringBuilder();
    Random randomGenerator = new Random();

    comment.append(
        subjects[randomGenerator.nextInt(subjects.length)] +
            " " +
            verbs[randomGenerator.nextInt(verbs.length)] +
            " " +
            references[randomGenerator.nextInt(references.length)] +
            " and it was "
    );

    if (rating <= 2) {
      comment.append(negativeAdjectives[randomGenerator.nextInt(negativeAdjectives.length)]);
    } else if (rating == 3) {
      comment.append(neutralAdjectives[randomGenerator.nextInt(neutralAdjectives.length)]);
    } else if (rating >= 4) {
      comment.append(positiveAdjectives[randomGenerator.nextInt(positiveAdjectives.length)]);
    } else {
      comment.append("not rated correctly");
    }

    comment.append("!!");

    return comment.toString();
  }

  /**
   * Finds a random date between two date bounds.
   *
   * @param startInclusive - the beginning bound
   * @param endExclusive   - the ending bound
   * @return - a random date as a LocalDate
   */
  private static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
    long startEpochDay = startInclusive.toEpochDay();
    long endEpochDay = endExclusive.toEpochDay();
    long randomDay = ThreadLocalRandom
        .current()
        .nextLong(startEpochDay, endEpochDay);

    return LocalDate.ofEpochDay(randomDay);
  }

  /**
   * Determines a random review date between the two given bounds.
   *
   * @return - a LocalDate
   */
  public static LocalDate getReviewDate(String productRelease) {
    LocalDate start = LocalDate.parse(productRelease);
    LocalDate end = LocalDate.now();
    return between(start, end);
  }

  public List<Review> generateRandomReviews(
      ProductRepository productRepository,
      Integer maxReviewsPerProduct,
      int userRepositoryLength
  ) {

    Random randomGenerator = new Random();
    List<Review> reviewList = new ArrayList<>();
    int reviewsPerProduct;

    for (int pId = 1; pId < productRepository.count(); pId++) {
      reviewsPerProduct = randomGenerator.nextInt(maxReviewsPerProduct);
      for (int j = 0; j < reviewsPerProduct; j++) {
        reviewList.add(createRandomReview(productRepository, pId, userRepositoryLength));
      }
    }

    return reviewList;
  }

  public Review createRandomReview(
      ProductRepository productRepository,
      int pId,
      int userRepositoryLength) {
    Review review = new Review();
    Product product = productRepository.getProductById((long) pId);

    Long productId = (long) pId;
    Long userId = ReviewFactory.getUserId(userRepositoryLength);
    Integer rating = ReviewFactory.getRating();
    String comment = ReviewFactory.getComment(rating);
    LocalDate reviewDate = ReviewFactory.getReviewDate(product.getReleaseDate());

    review.setProductId(productId);
    review.setUserId(userId);
    review.setRating(rating);
    review.setTitle("Cool, it's a " + product.getName());
    review.setComment(comment);
    review.setDate(reviewDate);

    return review;
  }
}
