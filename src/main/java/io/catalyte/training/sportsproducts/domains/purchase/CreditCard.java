package io.catalyte.training.sportsproducts.domains.purchase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

  /**
   * Checks if card is a Visa or Mastercard. No other networks currently supported.
   *
   * @return Credit network, or message stating unsupported network
   */
  public String getCardNetwork() {
    if (this.getCardNumber() < 1000000000000000L || this.getCardNumber() > 9999999999999999L) {
      return "Unsupported Credit Network";
    }
    int cardNetwork = (int) Math.floor(this.getCardNumber() / 1000000000000000L);
    switch (cardNetwork) {
      case 4:
        return "VISA";
      case 5:
        return "MASTERCARD";
      default:
        return "Unsupported Credit Network";
    }
  }

  /**
   * Validates Credit Card information
   *
   * @return true if all information is valid, descriptive exception thrown if any fields do not
   * match
   */
  boolean validateCreditCard() {
    if (this.validateCardExists() &&
        this.validateCardNumber() &&
        this.validateCvv() &&
        this.validateExpirationDate() &&
        this.validateCardholder()) {
      return true;
    } else {
      this.getErrorCode();
      return false;
    }
  }

  boolean validateCardExists() {
    if (this == null) {
      return false;
    } else {
      return true;
    }
  }

  boolean validateCardNumber() {
    long cardNumber = this.getCardNumber();
    String cardNetwork = this.getCardNetwork();

    if (cardNumber == 0L || (cardNetwork != "VISA" && cardNetwork != "MASTERCARD")) {
      return false;
    } else {
      return true;
    }
  }

  boolean validateCvv() {
    if (this.getCvv() < 100 || this.getCvv() >= 1000) {
      return false;
    } else {
      return true;
    }
  }

  boolean validateExpirationDate() {
    String cardExpiration = this.getExpiration().trim();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
    dateFormat.setLenient(false);
    Date expiry = null;

    if (cardExpiration == null || this.getExpiration().equals(""))

    try {
      expiry = dateFormat.parse(cardExpiration);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return expiry.before(new Date());
  }

  boolean validateCardholder() {
    if (this.getCardholder() == null || this.getCardholder().trim().equals("")) {
      return false;
    } else {
      return true;
    }
  }

  String getErrorCode() {
    String invalidCardMessage = "";
    invalidCardMessage = "No credit information provided";
    invalidCardMessage = "Card number must be at least 16 digits";
    invalidCardMessage = "Cvv number must be exactly 3 digits";
    invalidCardMessage = "Name field must not be empty";
    invalidCardMessage = "Expiration field must not be left empty";
    invalidCardMessage = "Card is expired";
    return invalidCardMessage;
  }
}
