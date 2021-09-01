package io.catalyte.training.sportsproducts.data;


import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.review.Review;
import java.time.LocalDate;
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
   * @return a product rating int
   */
  public static Long getUserId(int userRepositoryLength) {
    Random randomGenerator = new Random();
    return (long) randomGenerator.nextInt(userRepositoryLength) + 1;
  }

  /**
   * Returns a random product rating.
   *
   * @return a product rating int
   */
  public static Integer getRating() {
    Random randomGenerator = new Random();
    return randomGenerator.nextInt(5) + 1;
  }

  /**
   * Generates a random comment built from a list of word libraries.<br/> - rating 1 or 2 = negative
   * comment<br/> - rating 3 = neutral comment<br/> - rating 4 or 5 = positive comment
   *
   * @param rating product rating used to determine library to be used
   * @return a product comment
   */
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
   * @param startInclusive the beginning bound
   * @param endExclusive   the ending bound
   * @return a random date as a LocalDate
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
   * Determines a random review date between the product release date and the current local date.
   *
   * @return a LocalDate
   */
  public static LocalDate getReviewDate(String productRelease) {
    LocalDate start = LocalDate.parse(productRelease);
    LocalDate end = LocalDate.now();
    return between(start, end);
  }

  /**
   * Generates a random number of randomized reviews for a given product.
   *
   * @param product              the product to create reviews for
   * @param maxReviewsPerProduct specified upper limit of reviews to be generated for the product
   * @param userCount            current number of users in the database
   * @return a list of randomized reviews
   */
  public List<Review> generateRandomReviews(Product product, Integer maxReviewsPerProduct,
      Integer userCount) {
    Random randomGenerator = new Random();
    List<Review> reviewList = new ArrayList<>();

    int reviewsPerProduct = randomGenerator.nextInt(maxReviewsPerProduct);
    for (int j = 0; j < reviewsPerProduct; j++) {
      reviewList.add(createRandomReview(product, userCount));
    }

    return reviewList;
  }

  /**
   * Builds a random review for a given product and assigns a random user ID to the review based on
   * the given number of users in the database.
   *
   * @param product   the product to create the review for
   * @param userCount current number of users in the database
   * @return a randomized review
   */
  public Review createRandomReview(Product product, Integer userCount) {
    Review review = new Review();

    Long userId = ReviewFactory.getUserId(userCount);
    Integer rating = ReviewFactory.getRating();
    String comment = ReviewFactory.getComment(rating);
    LocalDate reviewDate = ReviewFactory.getReviewDate(product.getReleaseDate());

    review.setProductId(product.getId());
    review.setUserId(userId);
    review.setRating(rating);
    review.setTitle("Cool, it's a " + product.getName());
    review.setComment(comment);
    review.setDate(reviewDate);

    return review;
  }
}
