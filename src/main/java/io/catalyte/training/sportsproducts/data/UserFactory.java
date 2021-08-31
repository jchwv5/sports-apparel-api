package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.domains.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class provides tools for random generation of users.
 */
public class UserFactory {

  private static final String[] firstNames = {
      "Adael", "Ajani", "Akshaya", "Alaura", "Alayiah", "Aliya", "Amaia", "Andres", "Ani", "Ania",
      "Antonino", "Arie", "Ariyah", "Arlan", "Azael", "Aziyah",
      "Blake", "Blessing", "Brylie", "Bryson",
      "Cache", "Calee", "Cameron", "Chloe", "Chloey", "Claudia", "Corben", "Cristiano",
      "Dalton", "Demarco", "Dina",
      "Eliam", "Eloise", "Emery", "Emmy", "Eriana",
      "Finnley", "Forever",
      "Galilee", "Gwendolyn",
      "Hafsah", "Hardy",
      "Ignacio", "Ilyas",
      "Javier", "Jaycen", "Joey", "Juliet",
      "Kade", "Kaisley", "Kaison", "Kalina", "Karl", "Kayzlee", "Keston", "Khloey", "Kimora",
      "Kylee", "Kyngston", "Kynzleigh",
      "Laci", "Ladarius", "Lenox",
      "Mack", "Margo", "Mason", "Maylin", "Meliah", "Mikael", "Miriam", "Mohammed", "Moira",
      "Naia", "Naimah", "Naliyah", "Niamh",
      "Othniel", "Otis", "Paolo",
      "Rebekah", "Riley", "Rishik", "Rodney", "Ryli",
      "Samantha", "Sameera", "Saul", "Stormie",
      "Taj", "Tehilla", "Theodore", "Thorin", "Ty",
      "Unique",
      "Vishnu", "Vladislav",
      "Wren",
      "Yohana",
      "Zailynn", "Zayden",
  };

  private static final String[] lastNames = {
      "Anglin", "Athey",
      "Blackford", "Board", "Bourne", "Brinkerhoff", "Bristow", "Brunelle", "Buckley", "Burleson",
      "Byars",
      "Campana", "Carlsen", "Carpio", "Carswell", "Cathey", "Chabot", "Dennison", "Dickson", "Dove",
      "Dover", "Dubose",
      "Eckstein", "Eldred",
      "Fasano", "Flynn", "Fuentes", "Fullmer", "Fuson",
      "Gales", "Galligan", "Gish", "Gong", "Grams", "Gray", "Gruver",
      "Hacker", "Hafer", "Helmer", "Henriquez", "Hinds", "Hixon", "Householder", "Hutchens",
      "Judge",
      "Kerner", "Kinder", "Kutz",
      "Lindahl", "Loggins", "Luckett", "Lykins",
      "Matlock", "Mayo", "Mcclellan", "Mckeown", "Mcmillion", "Mcpeak", "Millican", "Milton",
      "Monreal", "Morel", "Mosby", "Mulder", "Mullinax",
      "Newell",
      "Oman", "Otoole",
      "Peavy", "Penn", "Plumb",
      "Quackenbush", "Queen",
      "Race", "Reis", "Rideout", "Roeder", "Rust",
      "Salvador", "Sauls", "Schott", "Settles", "Shea", "Shumate", "Sivesh", "Souza", "Sturdivant",
      "Swisher", "Switzer",
      "Theodore", "Truong",
      "Villareal", "Visser", "Vollmer",
      "Wages", "Waller", "Walz", "Whitt", "Wilkins",
      "Zamora"
  };

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

  private static final String[] cityBases = {
      "Arbor",
      "Ash",
      "Bass",
      "Birch",
      "Cherry",
      "Chestnut",
      "Elm",
      "Fir",
      "Hickory",
      "Maple",
      "Oak",
      "Spruce",
      "Willow"
  };

  private static final String[] cityMods = {
      "mond",
      "land",
      "oh",
      " Rock",
      "ron",
      "son",
      "ster",
      "tia",
      "ton",
      "town",
      "ville",
      "wood"
  };

  /**
   * Returns a random first name.
   *
   * @return - a first name from the firstNames library
   */
  public static String getFirstName() {
    Random randomGenerator = new Random();
    return firstNames[randomGenerator.nextInt(firstNames.length)];
  }

  /**
   * Returns a random last name.
   *
   * @return - a last name from the lastNames library
   */
  public static String getLastName() {
    Random randomGenerator = new Random();
    return lastNames[randomGenerator.nextInt(lastNames.length)];
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
   * Returns a random city name.
   *
   * @return a city string built from the cityBases and cityMods library
   */
  public static String getCity() {
    Random randomGenerator = new Random();
    StringBuilder cityName = new StringBuilder();

    cityName.append(cityBases[randomGenerator.nextInt(cityBases.length)]);
    cityName.append(cityMods[randomGenerator.nextInt(cityMods.length)]);

    return cityName.toString();
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

    return ((int) (Math.random() * ((999 - 111) + 1)) + 111)
        + "-"
        + ((int) (Math.random() * ((999 - 111) + 1)) + 111)
        + "-"
        + ((int) (Math.random() * ((9999 - 1111) + 1)) + 1111);
  }

  /**
   * Generates a random zip code.
   *
   * @param min lowest random zip
   * @param max highest random zip
   * @return random set of 5 numbers representing a zip code
   */
  public static String getZipCode(double min, double max) {
    return String.valueOf((int) ((Math.random() * ((max - min) + 1)) + min));
  }

  /**
   * Generates a random unique user code.
   *
   * @param lengthOffset - desired minimum length of code
   *
   * @return - a randomized user code
   */
  public static String generateUniqueUserCode(int lengthOffset) {
    final Random nameLength = new Random();
    final String lexicon = "abcdefghijklmnopqrstvwyz1234567890";
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
