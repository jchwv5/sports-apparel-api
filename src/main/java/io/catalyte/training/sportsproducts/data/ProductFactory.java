package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.domains.product.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This class provides tools for random generation of products.
 */
public class ProductFactory {

  private static final String[] colors = {
      "#000000", // white
      "#ffffff", // black
      "#39add1", // light blue
      "#3079ab", // dark blue
      "#c25975", // mauve
      "#e15258", // red
      "#f9845b", // orange
      "#838cc7", // lavender
      "#7d669e", // purple
      "#53bbb4", // aqua
      "#51b46d", // green
      "#e0ab18", // mustard
      "#637a91", // dark gray
      "#f092b0", // pink
      "#b7c0c7"  // light gray
  };
  private static final String[] demographics = {
      "Men",
      "Women",
      "Kids"
  };
  private static final String[] categories = {
      "Golf",
      "Soccer",
      "Basketball",
      "Hockey",
      "Football",
      "Running",
      "Baseball",
      "Skateboarding",
      "Boxing",
      "Weightlifting"
  };
  private static final String[] adjectives = {
      "Lightweight",
      "Slim",
      "Shock Absorbing",
      "Exotic",
      "Elastic",
      "Fashionable",
      "Trendy",
      "Next Gen",
      "Colorful",
      "Comfortable",
      "Water Resistant",
      "Wicking",
      "Heavy Duty"
  };

  private static final String[] brands = {
      "Adidas",
      "Nike",
      "Puma",
      "Under Armour",
      "Reebok",
      "New Balance",
      "Jordan",
      "Champion",
      "Columbia Sportswear",
      "Patagonia"
  };

  /**
   * Returns a random demographic from the list of demographics.
   *
   * @return - a demographic string
   */
  public static String getDemographic() {
    Random randomGenerator = new Random();
    return demographics[randomGenerator.nextInt(demographics.length)];
  }

  /**
   * Returns a random type from the list of types.
   *
   * @return - a type string
   */
  public static ProductType getType() {
    return ProductType.values()[new Random().nextInt(ProductType.values().length)];
  }

  /**
   * Returns a random adjective from the list of adjectives.
   *
   * @return - an adjective string
   */
  public static String getAdjective() {
    Random randomGenerator = new Random();
    return adjectives[randomGenerator.nextInt(adjectives.length)];
  }

  /**
   * Returns a random category from the list of categories.
   *
   * @return - a category string
   */
  public static String getCategory() {
    Random randomGenerator = new Random();
    return categories[randomGenerator.nextInt(categories.length)];
  }

  /**
   * Returns a random color code from the list of color codes.
   *
   * @return - a color string
   */
  public static String getColorCode() {
    Random randomGenerator = new Random();
    return colors[randomGenerator.nextInt(colors.length)];
  }

  /**
   * Returns a random brand from the list of brands.
   *
   * @return a brand string
   */
  public static String getBrand() {
    Random randomGenerator = new Random();
    return brands[randomGenerator.nextInt(brands.length)];
  }

  /**
   * Returns a random price from within a range.
   *
   * @return - a BigDecimal price
   */
  public static BigDecimal getPrice() {
    Random gen = new Random();
    return BigDecimal.valueOf(19.99 + (129.99 - 19.99) * gen.nextDouble())
        .setScale(2, RoundingMode.HALF_UP);
  }

  /**
   * @param min quantity of product
   * @param max quantity of product
   * @return random quantity of product
   */
  public static double getQuantity(double min, double max) {
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }

  /**
   * Generates a random product offering id.
   *
   * @return - a product offering id
   */
  public static String getRandomProductId() {
    return "po-" + RandomStringUtils.random(7, false, true);
  }

  /**
   * Generates a random style code.
   *
   * @return - a style code string
   */
  public static String getStyleCode() {
    return "sc" + RandomStringUtils.random(5, false, true);
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
   * Determines a random release date between the two given bounds.
   *
   * @return - a LocalDate
   */
  public static LocalDate getReleaseDate() {
    LocalDate start = LocalDate.of(2017, Month.JANUARY, 1);
    LocalDate end = LocalDate.now();
    return between(start, end);
  }

  /**
   * Random generates if a product active or not
   *
   * @return Boolean true or false
   */
  public static Boolean getActive() {
    Random randomGenerator = new Random();
    return randomGenerator.nextInt(2) == 1 ? Boolean.TRUE : Boolean.FALSE;
  }

  /**
   * randomly generates view count
   * @param min - minimum view count
   * @param max - maximum view count
   * @return - random view count
   */
  public static double getViewCount(double min, double max) {
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }

  /**
   * Generates a number of random products based on input.
   *
   * @param numberOfProducts - the number of random products to generate
   * @return - a list of random products
   */
  public List<Product> generateRandomProducts(Integer numberOfProducts) {

    List<Product> productList = new ArrayList<>();

    for (int i = 0; i < numberOfProducts; i++) {
      productList.add(createRandomProduct());
    }

    return productList;
  }

  /**
   * Uses random generators to build a product.
   *
   * @return - a randomly generated product
   */
  public Product createRandomProduct() {
    Product product = new Product();
    String category = ProductFactory.getCategory();
    String adjective = ProductFactory.getAdjective();
    ProductType productType = ProductFactory.getType();
    String type = productType.getTitle();
    String demographic = ProductFactory.getDemographic();
    String material = productType.getRandomMaterial().getTitle();

    product.setDescription("This " + category.toLowerCase() + " "
        + type.toLowerCase()
        + " for " + demographic.toLowerCase()
        + " is " + adjective.toLowerCase()
        + ". You're going to love it!");
    product.setCategory(category);
    product.setName(adjective + " " + category + " " + type);
    product.setType(type);
    product.setMaterial(material);
    product.setImageSrc(productType.getUrl());
    product.setPrice(getPrice());
    product.setBrand(getBrand());
    product.setQuantity((int) getQuantity(0, 99));
    product.setDemographic(demographic);
    product.setPrimaryColorCode(ProductFactory.getColorCode());
    product.setSecondaryColorCode(ProductFactory.getColorCode());
    product.setGlobalProductCode(ProductFactory.getRandomProductId());
    product.setStyleNumber(ProductFactory.getStyleCode());
    product.setReleaseDate(ProductFactory.getReleaseDate());
    product.setActive((ProductFactory.getActive()));
    product.setViewCount((int) getViewCount(0, 100));
    return product;
  }

}
