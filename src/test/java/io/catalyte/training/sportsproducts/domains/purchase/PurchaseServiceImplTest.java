package io.catalyte.training.sportsproducts.domains.purchase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.coyote.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.web.server.ResponseStatusException;

@RunWith(MockitoJUnitRunner.class)

public class PurchaseServiceImplTest {

  private final PurchaseServiceImpl testPurchaseValidation = new PurchaseServiceImpl(null, null,
      null);
  private final ProductFactory productFactory = new ProductFactory();
  List<Product> productList = productFactory.generateRandomProducts(4);
  private final CreditCard testCard = new CreditCard();
  private final PurchaseServiceImpl mockValidator = mock(PurchaseServiceImpl.class);

  Purchase purch1 = new Purchase();
  Purchase purch2 = new Purchase();
  Purchase purch3 = new Purchase();

  Product prod1 = productList.get(0);
  Product prod2 = productList.get(1);
  Product prod3 = productList.get(2);
  Product prod4 = productList.get(3);

  LineItem lineItem1 = new LineItem(101L, purch2, prod1, 5);
  LineItem lineItem2 = new LineItem(102L, purch2, prod2, 3);
  LineItem lineItem3 = new LineItem(103L, purch2, prod3, 1);
  LineItem lineItem4 = new LineItem(104L, purch3, prod4, 6);

  Set<LineItem> s1 = new HashSet<>();


  @InjectMocks
  private PurchaseServiceImpl mockPurchaseServiceImpl;

  @Mock
  private PurchaseRepository mockPurchaseRepository;

  List purchaseList = new ArrayList<>();
  Purchase p1 = new Purchase();
  Purchase p2 = new Purchase();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    DeliveryAddress d1 = new DeliveryAddress();
    d1.setFirstName("Jack");
    d1.setLastName("Smith");
    d1.setDeliveryStreet("123 South St");
    d1.setDeliveryStreet2("Apt 1");
    d1.setDeliveryCity("New York");
    d1.setDeliveryState("NY");
    d1.setDeliveryZip(12345);
    BillingAddress b1 = new BillingAddress();
    b1.setBillingStreet("123 South St");
    b1.setBillingStreet2("Apt 1");
    b1.setBillingCity("New York");
    b1.setBillingState("NY");
    b1.setBillingZip(12345);
    b1.setEmail("Jack@gmail.com");
    b1.setPhone("555-555-5555");
    CreditCard c1 = new CreditCard();
    c1.setCardNumber(1000000000000000L);
    c1.setCvv(510);
    c1.setExpiration("11/11/2011");
    c1.setCardholder("Jack Smith");
    p1.setId(1L);
    p1.setProducts(null);
    p1.setDeliveryAddress(d1);
    p1.setBillingAddress(b1);
    p1.setCreditCard(c1);
    purchaseList.add(p1);

    when(mockPurchaseRepository.findPurchasesByBillingAddressEmail(anyString())).thenReturn(
        purchaseList);

  }


  @Test
  public void checkForInactiveProductsEmptyPurchase() {
    purch1.setProducts(s1);
    Assertions.assertDoesNotThrow(() -> testPurchaseValidation.checkForInactiveProducts(purch1));
  }

  @Test
  public void checkForInactiveProductsAllProductsActive() {
    s1.add(lineItem1);
    s1.add(lineItem2);
    s1.add(lineItem3);
    purch2.setProducts(s1);
    prod1.setActive(true);
    prod2.setActive(true);
    prod3.setActive(true);
    Assertions.assertDoesNotThrow(() -> testPurchaseValidation.checkForInactiveProducts(purch2));
  }

  @Test
  public void checkForInactiveProductsAllProductsInactive() {
    s1.add(lineItem1);
    s1.add(lineItem2);
    s1.add(lineItem3);
    purch2.setProducts(s1);
    prod1.setActive(false);
    prod2.setActive(false);
    prod3.setActive(false);
    IllegalArgumentException e1 = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.checkForInactiveProducts(purch2));
    assert (e1.getMessage().contains("The following products in the purchase are inactive"));


  }

  @Test
  public void checkForInactiveProductsOneProductInactive() {
    s1.add(lineItem4);
    purch3.setProducts(s1);
    prod4.setActive(false);
    IllegalArgumentException e1 = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.checkForInactiveProducts(purch3));
    assert (e1.getMessage().contains("The following products in the purchase are inactive"));
  }

  @Test
  public void checkForInactiveProductsNotAllProductsInactive() {
    s1.add(lineItem1);
    s1.add(lineItem1);
    s1.add(lineItem3);
    purch2.setProducts(s1);
    prod1.setActive(true);
    prod2.setActive(false);
    prod3.setActive(false);
    IllegalArgumentException e2 = Assertions.assertThrows(IllegalArgumentException.class,
        () -> testPurchaseValidation.checkForInactiveProducts(purch2));
    assert (e2.getMessage().contains("The following products in the purchase are inactive"));
  }

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

  /**
   * Test that findAllPurchasesByEmail is returning the list of associated emails
   */
  @Test
  public void findPurchasesByEmailReturnsJack() {
    ArrayList<Purchase> expected = new ArrayList<>();
    expected.add(p1);
    List<Purchase> result = mockPurchaseServiceImpl.findPurchasesByEmail(
        p1.getBillingAddress().getEmail());
    Assert.assertEquals(expected, result);
  }

  /**
   * Test that finaAllPurchasesByEmail returns a 404 error when given an empty string
   */
  @Test(expected = ResourceNotFound.class)
  public void findPurchasesByEmailReturns404() {
    mockPurchaseServiceImpl.findPurchasesByEmail("");
  }

  /**
   * Test that findAllPurchasesByEmail returns a server error when catching a DataAccessException
   */
  @Test(expected = ServerError.class)
  public void findPurchasesByEmailReturnsDataAccessException() {
    Mockito.when(mockPurchaseRepository.findPurchasesByBillingAddressEmail(anyString()))
        .thenThrow(Mockito.mock(DataAccessException.class));
    mockPurchaseServiceImpl.findPurchasesByEmail("joe@gmail.com");
  }


}

