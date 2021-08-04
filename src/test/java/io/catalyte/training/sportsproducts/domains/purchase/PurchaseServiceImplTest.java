package io.catalyte.training.sportsproducts.domains.purchase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PurchaseServiceImplTest {
  private final CreditCard testCard = new CreditCard();
  private final PurchaseServiceImpl testPurchaseValidation = new PurchaseServiceImpl(null, null, null);
  private final PurchaseServiceImpl mockValidator = mock(PurchaseServiceImpl.class);

  @Test
  public void validateValidCardVisa() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    doNothing().when(mockValidator).validateCreditCard(testCard);
    mockValidator.validateCreditCard(testCard);
    verify(mockValidator, times(1)).validateCreditCard(testCard);
  }

  @Test
  public void validateValidCardMasterCard() {
    testCard.setCardNumber(5234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    doNothing().when(mockValidator).validateCreditCard(testCard);
    mockValidator.validateCreditCard(testCard);
    verify(mockValidator, times(1)).validateCreditCard(testCard);
  }

  @Test
  public void validateCardNumberNoInput() {
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Card number must have at least 16 digits");
  }

  @Test
  public void validateCardNumberTooShort() {
    testCard.setCardNumber(423456789123L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Card number must have at least 16 digits");
  }

  @Test
  public void validateCardNumberTooLong() {
    testCard.setCardNumber(423456789123456789L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage(
        "Transaction declined - Unsupported credit network or card number is invalid");
  }

  @Test
  public void validateCardOutOfNetwork() {
    testCard.setCardNumber(1234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage(
        "Transaction declined - Unsupported credit network or card number is invalid");
  }

  @Test
  public void validateCvvNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Cvv must be 3 digits");
  }

  @Test
  public void validateCvvTooShort() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(4);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Cvv must be 3 digits");
  }

  @Test
  public void validateCvvTooLong() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(4556);
    testCard.setExpiration("01/35");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Cvv must be 3 digits");
  }

  @Test
  public void validateExpiredCard() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/20");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Card is expired");
  }

  @Test
  public void validateExpirationNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be empty");
  }

  @Test
  public void validateExpirationEmpty() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be empty");
  }

  @Test
  public void validateExpirationOnlyWhitespace() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration(" ");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Expiration field must not be empty");
  }

  @Test
  public void validateExpirationInvalidInputLetters() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("abcdef");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Expiration input is invalid");
  }

  @Test
  public void validateExpirationInvalidInputDate() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("99/99");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Expiration input is invalid");
  }

  @Test
  public void validateExpirationInvalidInputAllNumbers() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("99999");
    testCard.setCardholder("Test User");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Expiration input is invalid");
  }

  @Test
  public void validateCardholderNoInput() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }

  @Test
  public void validateCardholderEmpty() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder("");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }

  @Test
  public void validateCardholderOnlyWhitespace() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(455);
    testCard.setExpiration("01/35");
    testCard.setCardholder(" ");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage("Transaction declined - Name field must not be empty");
  }

  @Test
  public void validateMultipleInvalidInputs() {
    testCard.setCardNumber(4234567891234567L);
    testCard.setCvv(4556);
    testCard.setExpiration("01/12");
    testCard.setCardholder(" ");

    IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.validateCreditCard(testCard));
    assertThat(e).hasMessage(
        "Transaction declined - Cvv must be 3 digits; Card is expired; Name field must not be empty");
  }
}