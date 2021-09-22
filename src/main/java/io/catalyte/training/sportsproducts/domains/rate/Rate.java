package io.catalyte.training.sportsproducts.domains.rate;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class represents a potential shipping or tax rate to be associated with a purchase.
 */
@Entity
public class Rate {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String type;

  private String code;

  private BigDecimal rate;

  private BigDecimal tax;

  public Rate() {
  }

  public Rate(String type, String code, BigDecimal rate, BigDecimal tax) {
    this.type = type;
    this.code = code;
    this.rate = rate;
    this.tax = tax;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public BigDecimal getTax() {
    return tax;
  }

  public void setTax(BigDecimal tax) {
    this.tax = tax;
  }

  @Override
  public String toString() {
    return "Rate{" +
        "id=" + id +
        ", type='" + type + '\'' +
        ", code='" + code + '\'' +
        ", rate=" + rate +
        '}';
  }
}
