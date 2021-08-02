package io.catalyte.training.sportsproducts.domains.product;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class is a representation of a sports apparel product.
 */
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private String demographic;

  private String category;

  private String type;

  private String releaseDate;

  private String primaryColorCode;

  private String secondaryColorCode;

  private String styleNumber;

  private String globalProductCode;

  private String brand;

  private String material;

  private BigDecimal price;

  private Integer quantity;

  private String imageSrc;

  private Boolean active;

  public Product() {
  }

  public Product(String name,
      String description,
      String demographic,
      String category,
      String type,
      String releaseDate,
      String primaryColorCode,
      String secondaryColorCode,
      String styleNumber,
      String globalProductCode,
      String brand, String material,
      BigDecimal price,
      Integer quantity,
      String imageSrc,
      Boolean active) {
    this.name = name;
    this.description = description;
    this.demographic = demographic;
    this.category = category;
    this.type = type;
    this.releaseDate = releaseDate;
    this.primaryColorCode = primaryColorCode;
    this.secondaryColorCode = secondaryColorCode;
    this.styleNumber = styleNumber;
    this.globalProductCode = globalProductCode;
    this.brand = brand;
    this.material = material;
    this.price = price;
    this.quantity = quantity;
    this.imageSrc = imageSrc;
    this.active = active;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Product product = (Product) o;

    if (name != null ? !name.equals(product.name) : product.name != null) {
      return false;
    }
    if (description != null ? !description.equals(product.description)
        : product.description != null) {
      return false;
    }
    if (demographic != null ? !demographic.equals(product.demographic)
        : product.demographic != null) {
      return false;
    }
    if (category != null ? !category.equals(product.category) : product.category != null) {
      return false;
    }
    if (type != null ? !type.equals(product.type) : product.type != null) {
      return false;
    }
    if (releaseDate != null ? !releaseDate.equals(product.releaseDate)
        : product.releaseDate != null) {
      return false;
    }
    if (primaryColorCode != null ? !primaryColorCode.equals(product.primaryColorCode)
        : product.primaryColorCode != null) {
      return false;
    }
    if (secondaryColorCode != null ? !secondaryColorCode.equals(product.secondaryColorCode)
        : product.secondaryColorCode != null) {
      return false;
    }
    if (styleNumber != null ? !styleNumber.equals(product.styleNumber)
        : product.styleNumber != null) {
      return false;
    }
    if (globalProductCode != null ? !globalProductCode.equals(product.globalProductCode)
        : product.globalProductCode != null) {
      return false;
    }
    if (!Objects.equals(brand, product.brand)) {
      return false;
    }
    if (material != null ? !material.equals(product.material)
        : product.material != null) {
      return false;
    }
    return active != null ? active.equals(product.active) : product.active == null;
  }


  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (demographic != null ? demographic.hashCode() : 0);
    result = 31 * result + (category != null ? category.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
    result = 31 * result + (primaryColorCode != null ? primaryColorCode.hashCode() : 0);
    result = 31 * result + (secondaryColorCode != null ? secondaryColorCode.hashCode() : 0);
    result = 31 * result + (styleNumber != null ? styleNumber.hashCode() : 0);
    result = 31 * result + (globalProductCode != null ? globalProductCode.hashCode() : 0);
    result = 31 * result + (active != null ? active.hashCode() : 0);
    result = 31 * result + (brand != null ? brand.hashCode() : 0);
    result = 31 * result + (material != null ? material.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", demographic='" + demographic + '\'' +
        ", category='" + category + '\'' +
        ", type='" + type + '\'' +
        ", releaseDate='" + releaseDate + '\'' +
        ", primaryColorCode='" + primaryColorCode + '\'' +
        ", secondaryColorCode='" + secondaryColorCode + '\'' +
        ", styleNumber='" + styleNumber + '\'' +
        ", globalProductCode='" + globalProductCode + '\'' +
        ", brand='" + brand + '\'' +
        ", brand='" + material + '\'' +
        ", active='" + active + '\'' +
        '}';
  }
}
