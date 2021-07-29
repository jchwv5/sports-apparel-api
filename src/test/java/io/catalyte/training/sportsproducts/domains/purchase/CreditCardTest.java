package io.catalyte.training.sportsproducts.domains.purchase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CreditCardTest {
  CreditCard testCard = new CreditCard();

  @Test
  public void validateValidCard() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    assertTrue(testCard.validateCreditCard());
  }

  @Test
  public void validateWithCardNumberNoInput() {
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Card number must be at least 16 digits");
  }

  @Test
  public void validateWithCardNumberTooShort() {
    testCard.setCardNumber(423456789123L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Card number must be at least 16 digits");
  }

  @Test
  public void validateWithCardOutOfNetwork() {
    testCard.setCardNumber(1234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Unsupported Credit Network");
  }

  @Test
  public void validateWithCvvNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Cvv number must be exactly 3 digits");
  }

  @Test
  public void validateWithCvvTooShort() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(4);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Cvv number must be exactly 3 digits");
  }

  @Test
  public void validateWithCvvTooLong() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(4556);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Cvv number must be exactly 3 digits");
  }

  @Test
  public void validateWithExpiredCard() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/20");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Card is expired");
  }

  @Test
  public void validateWithExpirationNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be left empty");
  }

  @Test
  public void validateWithExpirationEmpty() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be left empty");
  }

  @Test
  public void validateWithExpirationOnlyWhitespace() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration(" ");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be left empty");
  }

  @Test
  public void validateWithExpirationInvalidInputLetters() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("abcdef");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be left empty");
  }

  @Test
  public void validateWithCardholderNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }

  @Test
  public void validateWithCardholderEmpty() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }

  @Test
  public void validateWithCardholderOnlyWhitespace() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder(" ");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }
}