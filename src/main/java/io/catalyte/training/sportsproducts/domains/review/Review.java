package io.catalyte.training.sportsproducts.domains.review;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class represents a sports apparel product review.
 */
@Entity
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private Long productId;

  private Integer rating;

  private String title;

  private String comment;

  private Date date;

  public Review() {
  }

  public Review(
      Long id,
      Long userId,
      Long productId,
      Integer rating,
      String title,
      String comment,
      Date date) {
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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
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
