package io.catalyte.training.sportsproducts.domains.purchase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreditCardTest {
  CreditCard testCard = new CreditCard();

  @Test
  void getCardNetwork() {
  }

  @Test
  void validateValidCard() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    assertTrue(testCard.validateCreditCard());
  }

  @Test
  void validateWithCardNumberNoInput() {
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Card number must be at least 16 digits");
  }

  @Test
  void validateWithCardNumberTooShort() {
    testCard.setCardNumber(423456789123L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Card number must be at least 16 digits");
  }

  @Test
  void validateWithCardOutOfNetwork() {
    testCard.setCardNumber(1234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Unsupported Credit Network");
  }

  @Test
  void validateWithCvvNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Cvv number must be exactly 3 digits");
  }

  @Test
  void validateWithCvvTooShort() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(4);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Cvv number must be exactly 3 digits");
  }

  @Test
  void validateWithCvvTooLong() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(4556);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Cvv number must be exactly 3 digits");
  }

  @Test
  void validateWithExpiredCard() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/20");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Card is expired");
  }

  @Test
  void validateWithExpirationNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Card number must be at least 16 digits");
  }

  @Test
  void validateWithExpirationEmpty() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be left empty");
  }

  @Test
  void validateWithExpirationOnlyWhitespace() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration(" ");
    testCard.setCardholder("Test User");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be left empty");
  }

  @Test
  void validateWithCardholderNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }

  @Test
  void validateWithCardholderEmpty() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }

  @Test
  void validateWithCardholderOnlyWhitespace() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder(" ");

    RuntimeException e = Assertions.assertThrows(RuntimeException.class,
        () -> testCard.validateCreditCard());
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }
}