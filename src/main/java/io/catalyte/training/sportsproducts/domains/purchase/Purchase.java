package io.catalyte.training.sportsproducts.domains.purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Describes a purchase object that holds the information for a transaction
 */
@Entity
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "purchase")
  private Set<LineItem> products;

  @Embedded
  private DeliveryAddress deliveryAddress;

  @Embedded
  private BillingAddress billingAddress;

  @Embedded
  private CreditCard creditCard;

  private BigDecimal total;

  @Column(columnDefinition = "timestamp with time zone")
  private LocalDateTime timeStamp;

  private BigDecimal taxRate;

  private BigDecimal taxTotal;

  private BigDecimal shippingSubtotal;

  private BigDecimal chargeDiscount;

  private BigDecimal totalCharges;


  public Purchase() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<LineItem> getProducts() {
    return products;
  }

  public void setProducts(Set<LineItem> products) {
    this.products = products;
  }

  public DeliveryAddress getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public BillingAddress getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(BillingAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public CreditCard getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public BigDecimal getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }

  public BigDecimal getTaxTotal() {
    return taxTotal;
  }

  public void setTaxTotal(BigDecimal taxTotal) {
    this.taxTotal = taxTotal;
  }


  public BigDecimal getShippingSubtotal() {
    return shippingSubtotal;
  }

  public void setShippingSubtotal(BigDecimal shippingSubtotal) {
    this.shippingSubtotal = shippingSubtotal;
  }

  public BigDecimal getChargeDiscount() { return chargeDiscount; }

  public void setChargeDiscount(BigDecimal chargeDiscount) { this.chargeDiscount = chargeDiscount; }

  public BigDecimal getTotalCharges() {
    return totalCharges;
  }

  public void setTotalCharges(BigDecimal totalCharges) {
    this.totalCharges = totalCharges;
  }

  @Override
  public String toString() {
    return "Purchase{" +
        "id=" + id +
        ", products=" + products +
        ", deliveryAddress=" + deliveryAddress +
        ", billingAddress=" + billingAddress +
        ", creditCard=" + creditCard +
        ", total=" + total +
        ", timeStamp=" + timeStamp +
        ", taxRate=" + taxRate +
        ", taxTotal=" + taxTotal +
        ", shippingSubtotal=" + shippingSubtotal +
        ", chargeDiscount=" + chargeDiscount +
        ", totalCharges=" + totalCharges +
        '}';
  }

}

/**
 * Describes the object for the delivery address of the purchase
 */
@Embeddable
class DeliveryAddress {

  private String firstName;
  private String lastName;
  private String deliveryStreet;
  private String deliveryStreet2;
  private String deliveryCity;
  private String deliveryState;
  private int deliveryZip;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDeliveryStreet() {
    return deliveryStreet;
  }

  public void setDeliveryStreet(String deliveryStreet) {
    this.deliveryStreet = deliveryStreet;
  }

  public String getDeliveryStreet2() {
    return deliveryStreet2;
  }

  public void setDeliveryStreet2(String deliveryStreet2) {
    this.deliveryStreet2 = deliveryStreet2;
  }

  public String getDeliveryCity() {
    return deliveryCity;
  }

  public void setDeliveryCity(String deliveryCity) {
    this.deliveryCity = deliveryCity;
  }

  public String getDeliveryState() {
    return deliveryState;
  }

  public void setDeliveryState(String deliveryState) {
    this.deliveryState = deliveryState;
  }

  public int getDeliveryZip() {
    return deliveryZip;
  }

  public void setDeliveryZip(int deliveryZip) {
    this.deliveryZip = deliveryZip;
  }
}

/**
 * Describes the object for the billing address of the purchase
 */
@Embeddable
class BillingAddress {

  private String billingStreet;
  private String billingStreet2;
  private String billingCity;
  private String billingState;
  private int billingZip;
  private String email;
  private String phone;

  public BillingAddress() {
  }

  public String getBillingStreet() {
    return billingStreet;
  }

  public void setBillingStreet(String billingStreet) {
    this.billingStreet = billingStreet;
  }

  public String getBillingStreet2() {
    return billingStreet2;
  }

  public void setBillingStreet2(String billingStreet2) {
    this.billingStreet2 = billingStreet2;
  }

  public String getBillingCity() {
    return billingCity;
  }

  public void setBillingCity(String billingCity) {
    this.billingCity = billingCity;
  }

  public String getBillingState() {
    return billingState;
  }

  public void setBillingState(String billingState) {
    this.billingState = billingState;
  }

  public int getBillingZip() {
    return billingZip;
  }

  public void setBillingZip(int billingZip) {
    this.billingZip = billingZip;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

}



