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

}
