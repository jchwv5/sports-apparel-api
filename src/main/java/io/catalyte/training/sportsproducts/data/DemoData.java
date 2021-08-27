package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.review.Review;
import io.catalyte.training.sportsproducts.domains.review.ReviewRepository;
import io.catalyte.training.sportsproducts.domains.user.User;
import io.catalyte.training.sportsproducts.domains.user.UserRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Because this class implements CommandLineRunner, the run method is executed as soon as the server
 * successfully starts, and before it begins accepting requests from the outside.
 * <p>
 * Here, we use this as a place to run some code that generates and saves a list of random products
 * into the database.
 */
@Component
public class DemoData implements CommandLineRunner {

  private final Logger logger = LogManager.getLogger(DemoData.class);
  ProductFactory productFactory = new ProductFactory();
  UserFactory userFactory = new UserFactory();
  ReviewFactory reviewFactory = new ReviewFactory();
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private Environment env;

  @Override
  public void run(String... strings) {
    // Retrieve the value of custom property in application.yml
    boolean loadData = Boolean.parseBoolean(env.getProperty("products.load"));

    if (loadData) {
      seedDatabase();
    }
  }

  private void seedDatabase() {
    int numberOfProducts;
    int numberOfUsers;
    int maxReviewsPerProduct;

    try {
      // Retrieve the value of custom property in application.yml
      numberOfProducts = Integer.parseInt(env.getProperty("products.number"));
    } catch (NumberFormatException nfe) {
      // If it's not a string, set it to be a default value
      numberOfProducts = 500;
    }

    try {
      // Retrieve the value of custom property in application.yml
      numberOfUsers = Integer.parseInt(env.getProperty("users.number"));
    } catch (NumberFormatException nfe) {
      // If it's not a string, set it to be a default value
      numberOfUsers = 50;
    }

    try {
      // Retrieve the value of custom property in application.yml
      maxReviewsPerProduct = Integer.parseInt(env.getProperty("reviews.maxPerProduct"));
    } catch (NumberFormatException nfe) {
      // If it's not a string, set it to be a default value
      maxReviewsPerProduct = 2;
    }

    // Generate products
    List<Product> productList = productFactory.generateRandomProducts(numberOfProducts);

    // Generate users
    List<User> userList = userFactory.generateRandomUsers(numberOfUsers);

    // Persist them to the database
    logger.info("Loading " + numberOfProducts + " products...");
    productRepository.saveAll(productList);
    logger.info("Loading " + numberOfUsers + " users...");
    userRepository.saveAll(userList);
    logger.info("Loading reviews for products...");

    //Generate reviews for the products in the repository
    List<Review> reviewList = reviewFactory.generateRandomReviews(
        productRepository,
        maxReviewsPerProduct,
        numberOfUsers);

    // Persist them to the database
    reviewRepository.saveAll(reviewList);
    logger.info("Data load completed. You can make requests now.");
  }

}
