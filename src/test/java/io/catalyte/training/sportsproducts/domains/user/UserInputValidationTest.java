package io.catalyte.training.sportsproducts.domains.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInputValidationTest {
  private static ValidatorFactory validatorFactory;
  private static Validator validator;

  @BeforeClass
  public static void createValidator(){
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @AfterClass
  public static void close(){
    validatorFactory.close();
  }

  @Test
  public void shouldHaveNoViolations(){
    User user = new User();
    user.setFirstName("John");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void firstNameIsNull(){
    User user = new User();
    user.setFirstName(null);
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void firstNameIsEmpty(){
    User user = new User();
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void firstNameIsBlank(){
    User user = new User();
    user.setFirstName("");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }
  @Test
  public void firstNameContainsOnlyNumbers(){
    User user = new User();
    user.setFirstName("33");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void firstNameContainsNotAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("#@");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void firstNameContainsOnlyAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("'- ");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void firstNameContainsNumbersAndAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("34'- ");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }
  @Test
  public void firstNameContainsLettersAndAllowedAndNotAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("ee@- ");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }
  @Test
  public void firstNameContainsLettersAndSpaces(){
    User user = new User();
    user.setFirstName("dg ");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }
  @Test
  public void firstNameContainsLettersAndHyphens(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }


  @Test
  public void streetAddressIsEmpty(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void streetAddress2IsEmpty(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }

  @Test
  public void lastNameIsNull(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName(null);
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void lastNameIsEmpty(){
    User user = new User();
    user.setFirstName("john");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void lastNameIsBlank(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName("");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }
  @Test
  public void lastNameContainsOnlyNumbers(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName("33");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void lastNameContainsNotAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName("#$");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void lastNameContainsOnlyAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName("'- ");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void lastNameContainsNumbersAndAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName("34'-");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }
  @Test
  public void lastNameContainsLettersAndAllowedAndNotAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName("smith@_-");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }
  @Test
  public void lastNameContainsLettersAndSpaces(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName("smith ");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }
  @Test
  public void lastNameContainsLettersAndHyphens(){
    User user = new User();
    user.setFirstName("john");
    user.setLastName("smith-");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 abc street");
    user.setStreetAddress2("apt 1");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }

  @Test
  public void cityIsEmpty(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void cityContainsNumbers(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New Y33");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void cityContainsNotAllowedSpecialCharacters(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New Y#@");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void cityContainsHyphens(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("Winston-Salem");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }

  @Test
  public void cityContainsApostrophes(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("Coeur d'Alene");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }

  @Test
  public void cityContainsSpaceAndApostrophes(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("Coeur d'Alene");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }
  @Test
  public void cityContainsSpace(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }

  @Test
  public void stateIsEmpty(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void stateIsSmallLetters(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("ny");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void stateIsMoreLetters(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NJH");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void stateIsNotValid(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("BJ");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void stateIsValid(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12324");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }

  @Test
  public void zipCodeIsLetters(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("gujs");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void zipCodeIsSixDigits(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("123456");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void zipCodeIsFiveDigitsPlusOneDigits(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12345-6");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void zipCodeIsValid(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("12345-6789");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), true);
  }

  @Test
  public void zipCodeIsNineDigitsWithNoHyphen(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("123456789");
    user.setPhoneNumber("111-111-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void phoneNumberHasLetters(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("123456789");
    user.setPhoneNumber("111-qfv-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void phoneNumberHasLessNumbers(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("123456789");
    user.setPhoneNumber("111-11-1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void phoneNumberHasSpecialCharacters(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("123456789");
    user.setPhoneNumber("111.11.1111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(violations.isEmpty(), false);
  }

  @Test
  public void phoneNumberHasHyphens(){
    User user = new User();
    user.setFirstName("dg-");
    user.setLastName("smith");
    user.setEmail("smith@gmail.com");
    user.setStreetAddress("123 main street");
    user.setCity("New York");
    user.setState("NY");
    user.setZipCode("123456789");
    user.setPhoneNumber("111-111-111");
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    assertEquals(!violations.isEmpty(), false);
  }


}
