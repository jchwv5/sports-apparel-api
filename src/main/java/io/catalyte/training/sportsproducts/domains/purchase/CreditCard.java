package io.catalyte.training.sportsproducts.domains.purchase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;

/**
 * Describes Credit Card information for transactions
 */
@Embeddable
public class CreditCard {

  private long cardNumber;
  private int cvv;
  private String expiration;
  private String cardholder;

  public CreditCard() {
  }

  public CreditCard(long cardNumber, int cvv, String expiration, String cardholder) {
    this.cardNumber = cardNumber;
    this.cvv = cvv;
    this.expiration = expiration;
    this.cardholder = cardholder;
  }

  public long getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(long cardNumber) {
    this.cardNumber = cardNumber;
  }

  public int getCvv() {
    return cvv;
  }

  public void setCvv(int cvv) {
    this.cvv = cvv;
  }

  public String getExpiration() {
    return expiration;
  }

  public void setExpiration(String expiration) {
    this.expiration = expiration;
  }

  public String getCardholder() {
    return cardholder;
  }

  public void setCardholder(String cardholder) {
    this.cardholder = cardholder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CreditCard that = (CreditCard) o;

    if (cardNumber != that.cardNumber) {
      return false;
    }
    if (cvv != that.cvv) {
      return false;
    }
    if (!expiration.equals(that.expiration)) {
      return false;
    }
    return cardholder.equals(that.cardholder);
  }

  @Override
  public int hashCode() {
    int result = (int) (cardNumber ^ (cardNumber >>> 32));
    result = 31 * result + cvv;
    result = 31 * result + expiration.hashCode();
    result = 31 * result + cardholder.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CreditCard{" +
        "cardNumber=" + cardNumber +
        ", cvv=" + cvv +
        ", expiration='" + expiration + '\'' +
        ", cardholder='" + cardholder + '\'' +
        '}';
  }

//  /**
//   * Validates Credit Card information
//   *
//   * @return true if all information is valid
//   */
//  boolean validateCreditCard() {
//    ArrayList<String> errors = new ArrayList<>();
//    this.validateCardNumber(errors);
//    this.validateCvv(errors);
//    this.validateExpirationDate(errors);
//    this.validateCardholder(errors);
//
//    if (errors.isEmpty()) {
//      return true;
//    } else {
//      declineTransaction(errors);
//      return false;
//    }
//  }
//
//  private void validateCardNumber(ArrayList<String> errors) {
//    long cardNumber = this.getCardNumber();
//    String cardNetwork = this.getCardNetwork();
//
//    if (cardNumber < 1000000000000000L) {
//      errors.add("Card number must have at least 16 digits");
//    } else if (!Objects.equals(cardNetwork, "VISA")
//        && !Objects.equals(cardNetwork, "MASTERCARD")) {
//      errors.add(cardNetwork + " or card number is invalid");
//    }
//  }
//
//  private void validateCvv(ArrayList<String> errors) {
//    if (!(this.getCvv() >= 100) || !(this.getCvv() < 1000)) {
//      errors.add("Cvv must be 3 digits");
//    }
//  }
//
//  private void validateExpirationDate(ArrayList<String> errors) {
//    String cardExpiration = this.getExpiration();
//
//    if (cardExpiration == null || cardExpiration.trim().equals("")) {
//      errors.add("Expiration field must not be empty");
//      return;
//    } else {
//      cardExpiration = cardExpiration.trim();
//    }
//
//    Date formattedCardExpiration = null;
//    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
//    dateFormat.setLenient(false);
//
//    if (!Pattern.matches("^(0[1-9]|1[0-2])/?([0-9]{2})$", cardExpiration)) {
//      errors.add("Expiration input is invalid");
//    } else {
//      try {
//        formattedCardExpiration = dateFormat.parse(cardExpiration);
//      } catch (ParseException e) {
//        e.printStackTrace();
//      }
//      assert formattedCardExpiration != null;
//      if (formattedCardExpiration.before(new Date())) {
//        errors.add("Card is expired");
//      }
//    }
//  }
//
//  private void validateCardholder(ArrayList<String> errors) {
//    if (this.getCardholder() == null || this.getCardholder().trim().equals("")) {
//      errors.add("Name field must not be empty");
//    }
//  }
//
//  /**
//   * Checks if card is a 16-digit Visa or Mastercard. No other networks currently supported.
//   *
//   * @return Credit network, or message stating "Unsupported credit network"
//   */
//  public String getCardNetwork() {
//    int cardNetwork = (int) Math.floor(this.getCardNumber() / 1000000000000000.0);
//    switch (cardNetwork) {
//      case 4:
//        return "VISA";
//      case 5:
//        return "MASTERCARD";
//      default:
//        return "Unsupported credit network";
//    }
//  }
//
//  /**
//   * Validation helper method to throw exception with appropriate message
//   *
//   * @param errors list of error messages detailing what caused validation to fail
//   */
//  void declineTransaction(ArrayList<String> errors) {
//    StringBuilder message = new StringBuilder();
//    for (int i = 0; i < errors.size(); i++) {
//      message.append(errors.get(i));
//      if (i < errors.size() - 1) {
//        message.append("; ");
//      }
//    }
//    throw new IllegalArgumentException("Transaction declined - " + message);
//  }
}
