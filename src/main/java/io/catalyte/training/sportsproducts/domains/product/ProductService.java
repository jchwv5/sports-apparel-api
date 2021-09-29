package io.catalyte.training.sportsproducts.domains.product;


import java.util.List;
import java.util.Map;

/**
 * This interface provides an abstraction layer for the Products Service
 */
public interface ProductService {

  List<Product> getProducts(Product product);


  Map<String, Object> findAllProducts(int pageNo, int pageSize);

  List<Product> getFilteredProducts(Product product);

  List<Product> getPopularProducts();

  Product getProductById(Long id);

  Long deleteProductById(Long id);

  List<String> getProductByCategory();

  List<String> getProductTypes();

  Product saveProduct(Product product);

  List<Product> updateProducts(List<Product> products);
}
