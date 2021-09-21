package io.catalyte.training.sportsproducts.domains.product;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository




public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
  @Query(value = "SELECT p FROM Product p WHERE p.active = true")
  Page<Product> findAllByActive(boolean active, Pageable pageable);
  Product getProductById(Long id);

  @Query("SELECT DISTINCT category FROM Product ORDER BY category")
  List<String> getProductByCategory();

  @Query("SELECT DISTINCT type FROM Product ORDER BY type")
  List<String> getProductByTypes();

}
