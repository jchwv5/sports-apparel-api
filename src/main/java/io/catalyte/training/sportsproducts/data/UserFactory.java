package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.domains.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class provides tools for random generation of users.
 */
public class UserFactory {

  private static final String[] states = {
      "AL", "AK", "AZ", "AR",
      "CA", "CO", "CT",
      "DE",
      "FL",
      "GA",
      "HI",
      "ID", "IL", "IN", "IA",
      "KS", "KY",
      "LA",
      "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
      "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND",
      "OH", "OK", "OR",
      "PA",
      "RI",
      "SC", "SD",
      "TN", "TX",
      "UT",
      "VT", "VA",
      "WA", "WV", "WI", "WY"
  };

  private static final String[] suffix = {
      "Avenue",
      "Boulevard",
      "Circle",
      "Lane",
      "Place",
      "Road",
      "Street",
      "Way"
  };

  /**
   * Returns a random first name.
   *
   * @return - a first name
   */
  public static String getFirstName() {
    Random randomGenerator = new Random();
    return generateName(randomGenerator.nextInt(7));
  }

  /**
   * Returns a random last name.
   *
   * @return - a last name
   */
  public static String getLastName() {
    Random randomGenerator = new Random();
    return generateName(randomGenerator.nextInt(12));
  }

  /**
   * Returns an email address based on first and last name.
   *
   * @return - an email string
   */
  public static String getEmail(String firstName, String lastName) {
    Random randomGenerator = new Random();
    StringBuilder email = new StringBuilder();

    email.append(firstName.charAt(0));
    email.append(lastName);
    email.append(randomGenerator.nextInt(999));
    email.append("@fakedataemail.com");
    return email.toString();
  }

  /**
   * Returns a random fake street address.
   *
   * @return - a color string
   */
  public static String getStreetAddress() {
    Random randomGenerator = new Random();
    StringBuilder address = new StringBuilder();

    address.append(randomGenerator.nextInt(9999));
    address.append(" Tester ");
    address.append(suffix[randomGenerator.nextInt(suffix.length)]);

    return address.toString();
  }

  /**
   * Returns a randomized fake city.
   *
   * @return a city string
   */
  public static String getCity() {
    return generateName(5);
  }

  /**
   * Returns a random state from the list of states.
   *
   * @return a state string
   */
  public static String getState() {
    Random randomGenerator = new Random();
    return states[randomGenerator.nextInt(states.length)];
  }

  /**
   * Returns a random 10-digit phone number.
   *
   * @return - a phone number string
   */
  public static String getPhoneNumber() {
    Random randomGenerator = new Random();
    StringBuilder phoneNumber = new StringBuilder();

    phoneNumber.append((int) (Math.random() * ((999 - 111) + 1)) + 111);
    phoneNumber.append("-");
    phoneNumber.append((int) (Math.random() * ((999 - 111) + 1)) + 111);
    phoneNumber.append("-");
    phoneNumber.append((int) (Math.random() * ((9999 - 1111) + 1)) + 1111);

    return phoneNumber.toString();
  }

  /**
   * @param min lowest random zip
   * @param max highest random zip
   * @return random set of 5 numbers representing a zip code
   */
  public static String getZipCode(double min, double max) {
    return String.valueOf((int) ((Math.random() * ((max - min) + 1)) + min));
  }

  public static String generateName(int lengthOffset) {
    final Random nameLength = new Random();
    final String lexicon = "abcdefghijklmnopqrstvwy";
    StringBuilder name = new StringBuilder();

    while (name.toString().length() == 0) {
      int length = nameLength.nextInt(5) + lengthOffset;
      for (int i = 0; i < length; i++) {
        name.append(lexicon.charAt(nameLength.nextInt(lexicon.length())));
      }
    }
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }

  /**
   * Generates a number of random users based on input.
   *
   * @param numberOfUsers - the number of random users to generate
   * @return - a list of random users
   */
  public List<User> generateRandomUsers(Integer numberOfUsers) {

    List<User> userList = new ArrayList<>();

    for (int i = 0; i < numberOfUsers; i++) {
      userList.add(createRandomUser());
    }

    return userList;
  }

  /**
   * Uses random generators to build a user.
   *
   * @return - a randomly generated user
   */
  public User createRandomUser() {
    User user = new User();
    String firstName = UserFactory.getFirstName();
    String lastName = UserFactory.getLastName();
    String email = UserFactory.getEmail(firstName, lastName);
    String streetAddress = UserFactory.getStreetAddress();
    String city = UserFactory.getCity();
    String state = UserFactory.getState();
    String zip = UserFactory.getZipCode(11111, 99999);
    String phoneNumber = UserFactory.getPhoneNumber();

    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setStreetAddress(streetAddress);
    user.setCity(city);
    user.setState(state);
    user.setZipCode(zip);
    user.setPhoneNumber(phoneNumber);

    return user;
  }

}
