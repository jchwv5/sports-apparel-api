package io.catalyte.training.sportsproducts.domains.product;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
    JpaSpecificationExecutor<Product> {

  @Query(value = "SELECT p FROM Product p WHERE p.active = true")
  Page<Product> findAllByActive(boolean active, Pageable pageable);

  Product getProductById(Long id);

  @Query("SELECT DISTINCT category FROM Product ORDER BY category")
  List<String> getProductByCategory();

  @Query("SELECT DISTINCT type FROM Product ORDER BY type")
  List<String> getProductByTypes();

  @Query(nativeQuery = true, value = "SELECT * FROM Product WHERE active = TRUE ORDER BY view_count DESC limit 4")
  List<Product> getPopularProducts();

  @Transactional
  Long deleteProductById(Long id);
}
