package io.catalyte.training.sportsproducts.domains.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository
    extends JpaRepository<Review, Long> {

//  @Query("SELECT DISTINCT id FROM Review ORDER BY id")
//  List<Review> getReviewsByProductId(Long id);
//
//  @Query("SELECT DISTINCT product_name FROM Review ORDER BY product_name")
//  List<Review> getReviewsByProductName(String productName);
}
