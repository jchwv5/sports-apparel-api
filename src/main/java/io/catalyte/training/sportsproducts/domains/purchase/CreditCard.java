package io.catalyte.training.sportsproducts.domains.purchase;

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
   * Checks the credit network of a card number with at least 16 digits
   *
   * @return Credit network as a String, or message stating it is an unsupported network
   */
  public String getCardNetwork(){
    if(this.getCardNumber() < 1000000000000000L)
      return "Unsupported credit Network";
    int cardNetwork = (int) Math.floor(this.getCardNumber());
    switch (cardNetwork){
      case 4: return "VISA";
      case 5: return "MASTERCARD";
      default: return "Unsupported Credit Network";
    }
  }

  /**
   * Checks if the credit card is valid
   *
   * @return true if all information is valid, descriptive exceptions thrown if any fields do not
   * match
   */
  public boolean validateCreditCard() {
    long cardNumber = this.getCardNumber();
    String cardNetwork = this.getCardNetwork();

    if (this == null
        || this.getCardholder() == null || this.getCardholder().equals("")
        || this.getExpiration() == null || this.getExpiration().equals("")) {
      return false;
    } else if (cardNumber < 1000000000000000L) {
      return false;
    } else if(cardNetwork != "VISA" || cardNetwork != "MASTERCARD"){
      return false;
    } else if (this.getCvv() < 100 || this.getCvv() >= 1000) {
      return false;
    } else {
      return true;
    }
  }
}
