package io.catalyte.training.sportsproducts.domains.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT DISTINCT category FROM Product ORDER BY category")
  List<String> getProductByCategory();

  @Query("SELECT DISTINCT type FROM Product ORDER BY type")
  List<String> getProductByTypes();

}
