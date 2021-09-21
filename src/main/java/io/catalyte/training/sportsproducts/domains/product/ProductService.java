package io.catalyte.training.sportsproducts.domains.product;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This interface provides an abstraction layer for the Products Service
 */
public interface ProductService {

  List<Product> getProducts(Product product);


  Map<String, Object> findAllProducts(int pageNo, int pageSize);
  List<Product> getFilteredProducts(Product product);


  Product getProductById(Long id);

  List<String> getProductByCategory();

  List<String> getProductTypes();

  Product saveProduct(Product product);
}
