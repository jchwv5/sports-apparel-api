package io.catalyte.training.sportsproducts.domains.review;

import java.sql.Timestamp;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

/**
 * This class represents a sports apparel product review.
 */
@Entity
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Required
  private Long userId;

  @Required
  private Long productId;

  @Required
  private Integer rating;

  private String title;

  @Column(columnDefinition = "TEXT")
  private String comment;

  @Required
  private LocalDate date;

  public Review() {
  }

  public Review(
      Long id,
      Long userId,
      Long productId,
      Integer rating,
      String title,
      String comment,
      LocalDate date) {
    this.id = id;
    this.userId = userId;
    this.productId = productId;
    this.rating = rating;
    this.title = title;
    this.comment = comment;
    this.date = date;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Review{" +
        "id=" + id +
        ", userId='" + userId + '\'' +
        ", productId='" + productId + '\'' +
        ", rating='" + rating + '\'' +
        ", title='" + title + '\'' +
        ", comment='" + comment + '\'' +
        ", date='" + date + '\'' +
        '}';
  }
}
