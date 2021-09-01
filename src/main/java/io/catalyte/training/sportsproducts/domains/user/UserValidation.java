package io.catalyte.training.sportsproducts.domains.user;

import java.util.ArrayList;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserValidation {

  /**
   * Validates a user's first name field.
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param user User being validated
   */
  public void validateFirstName(ArrayList<String> errors, User user) {
    String firstName = user.getFirstName();
    validateRequired(errors, firstName, "First name");
    validateAlphabetic(errors, firstName, "First name");
  }

  /**
   * Validates a user's first name field.
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param user User being validated
   */
  public void validateLastName(ArrayList<String> errors, User user) {
    String lastName = user.getLastName();
    validateRequired(errors, lastName, "First name");
    validateAlphabetic(errors, lastName, "First name");
  }

  /**
   * This function validates a user's email field.
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param user User being validated
   */
  public void validateEmail(ArrayList<String> errors, User user) {
    String email = user.getEmail();
    validateRequired(errors, email, "Email");
    if (!Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", email)) {
      errors.add("Invalid email. Email must be in either x@x.x or x@x format.");
    }
  }

  /**
   * Validates a user's street address field.
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param user User being validated
   */
  public void validateStreetAddress(ArrayList<String> errors, User user) {
    String streetAddress = user.getStreetAddress();
    validateRequired(errors, streetAddress, "Street address");
  }

  /**
   * Validates a user's street address field.
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param user User being validated
   */
  public void validateCity(ArrayList<String> errors, User user) {
    String city = user.getCity();
    validateRequired(errors, city, "City");
    validateAlphabetic(errors, city, "City");
  }

  /**
   * Validates a user's state field
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param user User being validated
   */
  public void validateState(ArrayList<String> errors, User user) {
    String state = user.getState();
    validateRequired(errors, state, "State");
    if (!Pattern.matches(
        "^(?-i:A[LKSZRAEP]|C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$",
        state)) {
      errors.add("Invalid State. Please enter a valid two-letter US state abbreviation.");
    }
  }

  /**
   * Validates a user's zip code field
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param user User being validated
   */
  public void validateZipCode(ArrayList<String> errors, User user) {
    String zipCode = user.getZipCode();
    validateRequired(errors, zipCode, "Zip code");
    if (!Pattern.matches("^[0-9]{5}(?:-[0-9]{4})?$", zipCode)) {
      errors.add(
          "Invalid Zip Code. Zip Code must be in either the xxxxx or xxxxx-xxxx format, where 'x' represents a digit.");
    }
  }

  /**
   * Validates a user's phone number field
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param user User being validated
   */
  public void validatePhoneNumber(ArrayList<String> errors, User user) {
    String phoneNumber = user.getPhoneNumber();
    if (!Pattern.matches("\"^\\d{3}-\\d{3}-\\d{4}$", phoneNumber)) {
      errors.add(
          "Invalid Phone Number. Phone Number must be in the xxx-xxx-xxxx format, where 'x' represents a digit.");
    }
  }

  /**
   * Validates required user fields.
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param requiredInputToValidate String that will be validated
   * @param inputNameForErrorMessage Name of the input to be used for the dynamic error message.
   */
  public void validateRequired(ArrayList<String> errors, String requiredInputToValidate,
      String inputNameForErrorMessage) {
    if (requiredInputToValidate == null || requiredInputToValidate.trim().equals("")) {
      errors.add(String.format("%s; may not be blank, empty or null", inputNameForErrorMessage));
    }
  }

  /**
   * Validates alphabetic user fields.
   *
   * @param errors An ArrayList of Strings containing the error messages.
   * @param requiredInputToValidate String that will be validated
   * @param inputNameForErrorMessage Name of the input to be used for the dynamic error message.
   */
  public void validateAlphabetic(ArrayList<String> errors, String requiredInputToValidate,
      String inputNameForErrorMessage) {
    if (!Pattern.matches("^([A-Za-z])[A-Za-z '-]*$", requiredInputToValidate)) {
      errors.add(String
          .format("Invalid Input. %s; may only allow letters, apostrophes, spaces, hyphens (-)",
              inputNameForErrorMessage));
    }
  }

  /**
   * Validates all user fields
   *
   * @param user User being validated
   */
  void validateUser(User user) {
    ArrayList<String> errors = new ArrayList<>();
    if (user == null) {
      errors.add("No user provided");
      processBadUserValidation(errors);

      validateFirstName(errors, user);
      validateLastName(errors, user);
      validateEmail(errors, user);
      validateStreetAddress(errors, user);
      validateCity(errors, user);
      validateState(errors, user);
      validateZipCode(errors, user);
      validatePhoneNumber(errors, user);

      if (!errors.isEmpty()) {
        processBadUserValidation(errors);
      }
    }
  }

  /**
   * Validation helper method to throw exception for creating user with appropriate message
   *
   * @param errors ArrayList of error messages detailing what caused validation to fail
   */
  public void processBadUserValidation(ArrayList<String> errors) {
    StringBuilder message = new StringBuilder();
    for (int i = 0; i < errors.size(); i++) {
      message.append(errors.get(i));
      if (i < errors.size() - 1) {
        message.append("; ");
      }
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "User could not be created - " + message);
  }

}

