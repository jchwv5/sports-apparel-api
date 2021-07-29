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
      return "Unsupported credit Network";
    }
    int cardNetwork = (int) Math.floor(this.getCardNumber()/1000000000000000L);
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
   * @return true if all information is valid, descriptive exceptions thrown if any fields do not
   * match
   */
  boolean validateCreditCard() {
    boolean cardIsValid = true;
    String invalidCardMessage = "";
    long cardNumber = this.getCardNumber();
    String cardNetwork = this.getCardNetwork();

    String cardExpiration = this.getExpiration();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
    simpleDateFormat.setLenient(false);
    Date expiry = null;
    try {
      expiry = simpleDateFormat.parse(cardExpiration);
    } catch (ParseException e) {
      e.printStackTrace();
      return false;
    }
    boolean cardIsExpired = expiry.before(new Date());

    if (this == null) {
      cardIsValid = false;
      invalidCardMessage = "Required fields must not be left empty";
    } else if (cardNumber < 1000000000000000L) {
      cardIsValid = false;
      invalidCardMessage = "Card number must be at least 16 digits";
    } else if (this.getCvv() < 100 || this.getCvv() >= 1000) {
      cardIsValid = false;
      invalidCardMessage = "Cvv number must be exactly 3 digits";
    } else if (this.getCardholder() == null || this.getCardholder().equals("")) {
      cardIsValid = false;
      invalidCardMessage = "Name field must not be empty";
    } else if (this.getExpiration() == null || this.getExpiration().equals("")) {
      cardIsValid = false;
      invalidCardMessage = "Expiration field must not be left empty";
    } else if (cardNetwork != "VISA" && cardNetwork != "MASTERCARD") {
      cardIsValid = false;
      invalidCardMessage = cardNetwork;
    } else if (cardIsExpired) {
      cardIsValid = false;
      invalidCardMessage = "Card is expired";
    }

    if(cardIsValid){
      return true;
    } else {
      throw new RuntimeException("Transaction declined - " + invalidCardMessage);
    }
  }
}
