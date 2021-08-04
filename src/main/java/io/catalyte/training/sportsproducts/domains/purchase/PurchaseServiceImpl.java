package io.catalyte.training.sportsproducts.domains.purchase;

import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductService;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import io.catalyte.training.sportsproducts.exceptions.BadRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService {

  private final Logger logger = LogManager.getLogger(PurchaseServiceImpl.class);

  PurchaseRepository purchaseRepository;
  ProductService productService;
  LineItemRepository lineItemRepository;

  @Autowired
  public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductService productService,
      LineItemRepository lineItemRepository) {
    this.purchaseRepository = purchaseRepository;
    this.productService = productService;
    this.lineItemRepository = lineItemRepository;
  }

  /**
   * Retrieves all purchases from the database
   *
   * @return
   */
  public List<Purchase> findAllPurchases() {
    try {
      return purchaseRepository.findAll();
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Persists a purchase to the database
   *
   * @param newPurchase - the purchase to persist
   * @return the persisted purchase with ids
   */
  public Purchase savePurchase(Purchase newPurchase) {
    try {
      validateCreditCard(newPurchase.getCreditCard());
    } catch (IllegalArgumentException e) {
      logger.error(e.getMessage());
      throw new BadRequest(e.getMessage());
    }

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
   * This helper method retrieves product information for each line item and persists it
   *
   * @param purchase - the purchase object to handle lineitems for
   */
  private void handleLineItems(Purchase purchase) {
    Set<LineItem> itemsList = purchase.getProducts();

    if (itemsList != null) {
      itemsList.forEach(lineItem -> {

        // retrieve full product information from the database
        Product product = productService.getProductById(lineItem.getProduct().getId());

        // set the product info into the lineitem
        if (product != null) {
          lineItem.setProduct(product);
        }

        // set the purchase on the line item
        lineItem.setPurchase(purchase);

        // persist the populated lineitem
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
   * Validates credit card before purchase is able to be saved
   *
   * @param ccToValidate - the credit card information to validate
   */
  void validateCreditCard(CreditCard ccToValidate) {
    if (ccToValidate == null) {
      throw new RuntimeException("Transaction Declined - No credit card provided");
    }
    ArrayList<String> errors = new ArrayList<>();
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
    throw new IllegalArgumentException("Transaction declined - " + message);
  }
}


