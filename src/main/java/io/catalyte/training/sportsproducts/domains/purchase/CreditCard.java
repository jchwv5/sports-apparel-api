package io.catalyte.training.sportsproducts.domains.purchase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

  /**
   * Checks if card is a Visa or Mastercard. No other networks currently supported.
   *
   * @return Credit network, or message stating unsupported network
   */
  public String getCardNetwork() {
    int cardNetwork = (int) Math.floor(this.getCardNumber() / 1000000000000000L);
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
   * Validates Credit Card information
   *
   * @return true if all information is valid, descriptive exception thrown if any fields do not
   * match
   */
  boolean validateCreditCard() {
    return (
        this.validateCardNumber() &&
        this.validateCvv() &&
        this.validateExpirationDate() &&
        this.validateCardholder());
  }

  boolean validateCardNumber() {
    long cardNumber = this.getCardNumber();
    String cardNetwork = this.getCardNetwork();

    if (cardNumber < 1000000000000000L) {
      declineTransaction("Card number must have at least 16 digits");
    } else if (!Objects.equals(cardNetwork, "VISA")
        && !Objects.equals(cardNetwork, "MASTERCARD")) {
      declineTransaction(cardNetwork);
    }
    return true;
  }

  boolean validateCvv() {
    if (!(this.getCvv() >= 100) || !(this.getCvv() < 1000)) {
      declineTransaction("Cvv must be 3 digits");
    }
    return true;
  }

  boolean validateExpirationDate() {
    if (this.getExpiration() == null) {
      declineTransaction("Expiration field must not be left empty");
    }
    String cardExpiration = this.getExpiration().trim();
    Date formattedCardExpiration = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
    dateFormat.setLenient(false);

    if (cardExpiration.equals("")) {
      declineTransaction("Expiration field must not be left empty");
    } else if (!Pattern.matches("^(0[1-9]|1[0-2])/?([0-9]{2})$", cardExpiration)) {
      declineTransaction("Expiration input is invalid");
    } else {
      try {
        formattedCardExpiration = dateFormat.parse(cardExpiration);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    if (formattedCardExpiration.before(new Date())) {
      declineTransaction("Card is expired");
    }

    return true;
  }

  boolean validateCardholder() {
    if (this.getCardholder() == null || this.getCardholder().trim().equals("")) {
      declineTransaction("Name field must not be empty");
    }
    return true;
  }

  void declineTransaction(String message) {
    throw new RuntimeException("Transaction declined - " + message);
  }
}
