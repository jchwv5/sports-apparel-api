package io.catalyte.training.sportsproducts.domains.promotion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * This class is a representation of a promotion information.
 */
@Entity
@Table(name = "Promotion")
@JsonInclude(Include.NON_NULL)
public class Promotion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "promotion_code")
  @NotBlank(message = "code is required")
  private String code;

  @Column(name = "discount_type")
  @NotBlank(message = "type is required")
  private String type;

  @Column(name = "discount_percentage")
  private String percentage;

  @Column(name = "discount_dollar_amount")
  private String flat;

  public Promotion() {

  }

  public Promotion(String code, String type, String percentage, String flat) {
    this.code = code;
    this.type = type;
    this.percentage = percentage;
    this.flat = flat;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPercentage() {
    return percentage;
  }

  public void setPercentage(String percentage) {
    this.percentage = percentage;
  }

  public String getFlat() {
    return flat;
  }

  public void setFlat(String flat) {
    this.flat = flat;
  }

  @Override
  public String toString() {
    return "Promotion{" +
        "id=" + id +
        ", code='" + code + '\'' +
        ", type='" + type + '\'' +
        ", percentage='" + percentage + '\'' +
        ", flat='" + flat + '\'' +
        '}';
  }
}
