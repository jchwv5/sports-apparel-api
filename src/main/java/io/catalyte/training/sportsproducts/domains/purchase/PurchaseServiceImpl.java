package io.catalyte.training.sportsproducts.domains.purchase;

import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductService;
import io.catalyte.training.sportsproducts.domains.rate.Rate;
import io.catalyte.training.sportsproducts.domains.rate.RateService;
import io.catalyte.training.sportsproducts.domains.user.User;
import io.catalyte.training.sportsproducts.domains.user.UserRepository;
import io.catalyte.training.sportsproducts.domains.user.UserValidation;
import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import io.catalyte.training.sportsproducts.exceptions.UnprocessableEntityError;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PurchaseServiceImpl implements PurchaseService {

  static final String AK = "Alaska";
  static final String HI = "Hawaii";
  static final BigDecimal PRICE_1 = BigDecimal.valueOf(50.00);


  private final Logger logger = LogManager.getLogger(PurchaseServiceImpl.class);
  private final UserValidation userValidation = new UserValidation();

  private final PurchaseRepository purchaseRepository;
  private final ProductService productService;
  private final LineItemRepository lineItemRepository;
  private final UserRepository userRepository;

  private RateService rateService;

  @Autowired
  public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductService productService,
      LineItemRepository lineItemRepository, UserRepository userRepository,
      RateService rateService) {
    this.purchaseRepository = purchaseRepository;
    this.productService = productService;
    this.lineItemRepository = lineItemRepository;
    this.userRepository = userRepository;
    this.rateService = rateService;
  }

  /**
   * Retrieves all purchases from the database with associated email matching request parameter
   *
   * @param email email to find all associated purchases for
   * @return list of all purchases with matching email
   */
  public List<Purchase> findPurchasesByEmail(String email) {
    if (email == null || email.equals("")) {
      throw new ResourceNotFound("No email specified for request.");
    }
    try {
      return purchaseRepository.findPurchasesByBillingAddressEmail(email);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * This method will search the line_item table looking for purchases that contain a product that
   * matches the ID provided
   * @param id - The ID of the product to check for purchases
   * @return
   */

  @Override
  public List<LineItem> findPurchasesByProductId(Long id) {
    if (id == null || id.equals("")) throw new ResourceNotFound("No ID specified for request.");
    try {
      return lineItemRepository.findPurchasesByProductId(id);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Persists a purchase to the database and last active timestamp to user table
   *
   * @param newPurchase - the purchase to persist
   * @return the persisted purchase with ids
   */
  public Purchase savePurchase(Purchase newPurchase) {
    try {
      checkForInactiveProducts(newPurchase);
    } catch (IllegalArgumentException e) {
      logger.error(e.getMessage());
      throw new UnprocessableEntityError(e.getMessage());
    }

    try {
      validateCreditCard(newPurchase.getCreditCard());
    } catch (IllegalArgumentException e) {
      logger.error(e.getMessage());
      throw new BadRequest(e.getMessage());
    }

    //  Calculate and set purchase total
    BigDecimal total = calculateTotal(newPurchase);
    BigDecimal total2 = total.setScale(2, BigDecimal.ROUND_HALF_UP);
    newPurchase.setTotal(total2);


    //Time stamp of when purchase is made
    ZoneId zid = ZoneId.of("UTC");
    LocalDateTime lt = LocalDateTime.now(zid);
    newPurchase.setTimeStamp(lt);

    // update user timestamp after making a purchase
    addUserWithPurchase(newPurchase, lt);

    try {
      purchaseRepository.save(newPurchase);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
    // after the purchase is persisted and has an id, we need to handle its lineitems and persist them as well
    handleLineItems(newPurchase);
    return newPurchase;
  }

  /**
   * Persists a user with purchase and last active timestamp to user table
   *
   * @param newPurchase - user's purchase
   * @param lt          - localDateTime in UTC
   */
  public void addUserWithPurchase(Purchase newPurchase, LocalDateTime lt) {
    // SEE IF USER EXISTS
    User existingUser;
    try {
      existingUser = userRepository.findByEmail(newPurchase.getBillingAddress().getEmail());
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }

    // IF USER EXISTS, RETURN EXISTING USER WITH UPDATED TIMESTAMP
    if (existingUser != null) {
      logger.info("Existing user has been found");
      existingUser.setLastActiveTime(lt);
      userRepository.save(existingUser);
      // IF THE USER DOESN'T EXIST, CREATE A NEW USER IN THE USER TABLE
    } else {
      User newUser = new User();
      newUser.setLastName(newPurchase.getDeliveryAddress().getLastName());
      newUser.setFirstName(newPurchase.getDeliveryAddress().getFirstName());
      newUser.setStreetAddress(newPurchase.getBillingAddress().getBillingStreet());
      newUser.setStreetAddress2(newPurchase.getBillingAddress().getBillingStreet2());
      newUser.setCity(newPurchase.getBillingAddress().getBillingCity());

      // set up two-letter abbreviation state for user
      String stateFullName = newPurchase.getBillingAddress().getBillingState();
      StateAbbreviation abbreviation = new StateAbbreviation();
      String state = abbreviation.convertStateAbbreviations(stateFullName.toUpperCase());
      newUser.setState(state);

      //convert zipcode of type int to type String
      int zipCode = newPurchase.getBillingAddress().getBillingZip();
      String zipCodeStr = String.valueOf(zipCode);
      newUser.setZipCode(zipCodeStr);
      newUser.setPhoneNumber(newPurchase.getBillingAddress().getPhone());
      newUser.setRole("Customer");
      newUser.setEmail(newPurchase.getBillingAddress().getEmail());
      newUser.setLastActiveTime(lt);
      userValidation.validateUser(newUser);
      try {
        userRepository.save(newUser);
      } catch (DataAccessException e) {
        logger.error(e.getMessage());
        throw new ServerError(e.getMessage());
      }
    }
  }

  /**
   * This helper method retrieves product information for each line item and persists it
   *
   * @param purchase - the purchase object to handle line items for
   */
  private void handleLineItems(Purchase purchase) {
    Set<LineItem> itemsList = purchase.getProducts();

    if (itemsList != null) {
      itemsList.forEach(lineItem -> {

        // retrieve full product information from the database
        Product product = productService.getProductById(lineItem.getId());

        // set the product info into the line item
        if (product != null) {
          lineItem.setProduct(product);
        }

        // set the purchase on the line item
        lineItem.setPurchase(purchase);

        // persist the populated line item
        try {
          lineItemRepository.save(lineItem);
        } catch (DataAccessException e) {
          logger.error(e.getMessage());
          throw new ServerError(e.getMessage());
        }
      });
    }
  }

  /**
   * Calculates the total purchase cost
   *
   * @param purchase purchase to calculate total for
   * @return total purchase cost
   */
  private BigDecimal calculateTotal(Purchase purchase) {
    BigDecimal total = new BigDecimal(0);
    Set<LineItem> itemsList = purchase.getProducts();
    BigDecimal chargeDiscount = purchase.getChargeDiscount();

    if (itemsList != null) {
      for (LineItem lineItem : itemsList) {

        // retrieve full product information from the database
        BigDecimal itemPrice = productService.getProductById(lineItem.getId()).getPrice();
        BigDecimal itemSubtotal = new BigDecimal(0);
        int itemQuantity = lineItem.getQuantity();

        // get the subtotal of the line item
        itemSubtotal = itemPrice.multiply(BigDecimal.valueOf(itemQuantity));

        // add line item subtotal to purchase subtotal
        total = total.add(itemSubtotal);

        // use total, tax rate, and shipping rate to calc taxTotal, shippingSubtotal and totalCharges
        BigDecimal taxTotal = new BigDecimal(0);
        BigDecimal shippingTotal = new BigDecimal(0);
        BigDecimal totalCharges = new BigDecimal(0);
        String state = purchase.getDeliveryAddress().getDeliveryState();
        try {
          //get taxRate from DB
          List<Rate> listRate = rateService.getRateByCode(state);
          if (listRate.size() > 0) {
            Rate rate = listRate.get(0);
            BigDecimal taxRate = rate.getRate();

            //calculate tax charge for new purchase
            taxTotal = taxRate.multiply(total);
            BigDecimal taxTotal2 = taxTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
            purchase.setTaxTotal(taxTotal2);

            // set tax rate for new purchase
            BigDecimal taxRate2 = taxRate.multiply(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
            purchase.setTaxRate(taxRate2);
          }
        } catch (DataAccessException e) {
          logger.error("can't find state tax rate");
          throw new ServerError("can't find state tax rate");
        }

        try {
          //get shipping price
          if (total.compareTo(PRICE_1) <= 0 && (state.equalsIgnoreCase(AK) || state.equalsIgnoreCase(HI))) {// purchase price <= $50
            List<Rate> listShippingRate = rateService.getRateByCode("additional");// shipping = $15.00
            if (listShippingRate.size() > 0) {
              shippingTotal = listShippingRate.get(0).getRate();
            }
          } else if (total.compareTo(PRICE_1) > 0 && (state.equalsIgnoreCase(AK) || state.equalsIgnoreCase(HI))) { // purchase price > $50
            List<Rate> listShippingRate = rateService.getRateByCode("extended");// shipping = $10.00
            if (listShippingRate.size() > 0) {
              shippingTotal = listShippingRate.get(0).getRate();
            }
          } else if (total.compareTo(PRICE_1) <= 0) {// purchase price <= $50
            List<Rate> listShippingRate = rateService.getRateByCode("base");// shipping = $5.00
            if (listShippingRate.size() > 0) {
              shippingTotal = listShippingRate.get(0).getRate();
            }
          }
        } catch (DataAccessException e) {
          logger.error("can't find shipping rate");
          throw new ServerError("can't find shipping rate");
        }

        //set shipping price
        purchase.setShippingSubtotal(shippingTotal);

        //totalCharges
        totalCharges = total.add(taxTotal).add(shippingTotal).subtract(chargeDiscount);
        BigDecimal totalCharges2 = totalCharges.setScale(2, BigDecimal.ROUND_HALF_UP);
        purchase.setTotalCharges(totalCharges2);

      }
    }

    return total;
  }

  /**
   * Validates credit card before purchase is able to be saved
   *
   * @param ccToValidate - the credit card information to validate
   */
  void validateCreditCard(CreditCard ccToValidate) {
    ArrayList<String> errors = new ArrayList<>();
    if (ccToValidate == null) {
      errors.add("No credit card provided");
      declineTransaction(errors);
    }

    validateCardNumber(errors, ccToValidate);
    validateCvv(errors, ccToValidate);
    validateExpirationDate(errors, ccToValidate);
    validateCardholder(errors, ccToValidate);

    if (!errors.isEmpty()) {
      declineTransaction(errors);
    }
  }

  private void validateCardNumber(ArrayList<String> errors, CreditCard ccToValidate) {
    long cardNumber = ccToValidate.getCardNumber();
    String cardNetwork = getCardNetwork(ccToValidate);

    if (cardNumber < 1000000000000000L) {
      errors.add("Card number must have at least 16 digits");
    } else if (!Objects.equals(cardNetwork, "VISA")
        && !Objects.equals(cardNetwork, "MASTERCARD")) {
      errors.add(cardNetwork + " or card number is invalid");
    }
  }

  private void validateCvv(ArrayList<String> errors, CreditCard ccToValidate) {
    if (!(ccToValidate.getCvv() >= 100) || !(ccToValidate.getCvv() < 1000)) {
      errors.add("Cvv must be 3 digits");
    }
  }

  private void validateExpirationDate(ArrayList<String> errors, CreditCard ccToValidate) {
    String cardExpiration = ccToValidate.getExpiration();

    if (cardExpiration == null || cardExpiration.trim().equals("")) {
      errors.add("Expiration field must not be empty");
      return;
    } else {
      cardExpiration = cardExpiration.trim();
    }

    Date formattedCardExpiration = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
    dateFormat.setLenient(false);

    if (!Pattern.matches("^(0[1-9]|1[0-2])/?([0-9]{2})$", cardExpiration)) {
      errors.add("Expiration input is invalid");
    } else {
      try {
        formattedCardExpiration = dateFormat.parse(cardExpiration);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      assert formattedCardExpiration != null;
      if (formattedCardExpiration.before(new Date())) {
        errors.add("Card is expired");
      }
    }
  }

  private void validateCardholder(ArrayList<String> errors, CreditCard ccToValidate) {
    if (ccToValidate.getCardholder() == null || ccToValidate.getCardholder().trim().equals("")) {
      errors.add("Name field must not be empty");
    }
  }

  /**
   * Checks if card is a 16-digit Visa or Mastercard. No other networks currently supported.
   *
   * @return Credit network, or message stating "Unsupported credit network"
   */
  private String getCardNetwork(CreditCard ccToValidate) {
    int cardNetwork = (int) Math.floor(ccToValidate.getCardNumber() / 1000000000000000.0);
    switch (cardNetwork) {
      case 4:
        return "VISA";
      case 5:
        return "MASTERCARD";
      default:
        return "Unsupported credit network";
    }
  }

  /**
   * Validation helper method to throw exception with appropriate message
   *
   * @param errors list of error messages detailing what caused validation to fail
   */
  private void declineTransaction(ArrayList<String> errors) {
    StringBuilder message = new StringBuilder();
    for (int i = 0; i < errors.size(); i++) {
      message.append(errors.get(i));
      if (i < errors.size() - 1) {
        message.append("; ");
      }
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction declined - " + message);
  }

  /**
   * Checks the purchase for inactive products
   */
  public void checkForInactiveProducts(Purchase purchase) {
    String errorMessage = "The following products in the purchase are inactive: ";
    boolean inactiveProductPresent = false;

    Set<LineItem> itemList = purchase.getProducts();

    if (itemList != null) {
      for (LineItem lineItem : itemList) {
        Product product = productService.getProductById(lineItem.getId());
        if (!product.getActive()) {
          inactiveProductPresent = true;
          errorMessage = errorMessage + product.getName() + ", ";
        }
      }
    }

    if (inactiveProductPresent) {
      if (errorMessage.endsWith(", ")) {
        errorMessage = errorMessage.substring(0, errorMessage.length() - 2) + ".";
      }
      throw new IllegalArgumentException(errorMessage);

    }
  }

  /**
   * calculate total charges when user changes the state in front end
   *
   * @param purchase - a purchase object used for calculation
   * @return purchase - a purchase
   */
  public Purchase calculateTotalCharges(PurchaseForTaxCalculation purchase) {

    Purchase returnPurchase = new Purchase();

    logger.debug("calculateTotalCharges starts");
    logger.debug("input purchase is " + purchase);

    BigDecimal total = new BigDecimal(0);
    Set<LineItem> itemsList = purchase.getProducts();

    if (itemsList != null) {
      for (LineItem lineItem : itemsList) {

        // retrieve full product information from the database
        BigDecimal itemPrice = productService.getProductById(lineItem.getId()).getPrice();
        BigDecimal itemSubtotal = new BigDecimal(0);
        int itemQuantity = lineItem.getQuantity();

        // get the subtotal of the line item
        itemSubtotal = itemPrice.multiply(BigDecimal.valueOf(itemQuantity));

        // add line item subtotal to purchase subtotal
        total = total.add(itemSubtotal);
      }
    }
    BigDecimal total2 = total.setScale(2, BigDecimal.ROUND_HALF_UP);
    returnPurchase.setTotal(total2);

    BigDecimal taxTotal = new BigDecimal(0);
    BigDecimal shippingTotal = new BigDecimal(0);
    BigDecimal totalCharges = new BigDecimal(0);
    String state = purchase.getDeliveryState();
    try {
      //get taxRate from DB
      List<Rate> listRate = rateService.getRateByCode(state);
      if (listRate.size() > 0) {
        Rate rate = listRate.get(0);
        BigDecimal taxRate = rate.getRate();
        //calculate tax charge for new purchase
        taxTotal = taxRate.multiply(total);
        BigDecimal taxTotal2 = taxTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
        returnPurchase.setTaxTotal(taxTotal2);

        // set tax rate for new purchase
        BigDecimal taxRate2 = taxRate.multiply(new BigDecimal(100))
            .setScale(2, BigDecimal.ROUND_HALF_UP);

        returnPurchase.setTaxRate(taxRate2);
      }
    } catch (DataAccessException e) {
      logger.error("can't find state tax rate");
      throw new ServerError("can't find state tax rate");
    }

    try {
      //get shipping price
      if (total.compareTo(PRICE_1) <= 0 && (state.equalsIgnoreCase(AK) || state.equalsIgnoreCase(HI))) {// purchase price <= $50
        List<Rate> listShippingRate = rateService.getRateByCode("additional");// shipping = $15.00
        if (listShippingRate.size() > 0) {
          shippingTotal = listShippingRate.get(0).getRate();
        }
      } else if (total.compareTo(PRICE_1) > 0 && (state.equalsIgnoreCase(AK) || state.equalsIgnoreCase(HI))){ // purchase price > $50
        List<Rate> listShippingRate = rateService.getRateByCode("extended");// shipping = $10.00
        if (listShippingRate.size() > 0) {
          shippingTotal = listShippingRate.get(0).getRate();
        }
      } else if (total.compareTo(PRICE_1) <= 0) {
        List<Rate> listShippingRate = rateService.getRateByCode("base");// shipping = $5.00
        if (listShippingRate.size() > 0) {
          shippingTotal = listShippingRate.get(0).getRate();
        }
      }
    } catch (DataAccessException e) {
      logger.error("can't find shipping rate");
      throw new ServerError("can't find shipping rate");
    }

    //set shipping price
    returnPurchase.setShippingSubtotal(shippingTotal);

    //totalCharges
    totalCharges = total.add(taxTotal).add(shippingTotal);
    BigDecimal totalCharges2 = totalCharges.setScale(2, BigDecimal.ROUND_HALF_UP);
    returnPurchase.setTotalCharges(totalCharges2);

    logger.debug("after calculate purchase is " + purchase);
    logger.debug("calculateTotalCharges end");

    return returnPurchase;
  }
}

