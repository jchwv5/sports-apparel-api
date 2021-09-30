package io.catalyte.training.sportsproducts.domains.product;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * This class provides the implementation for the ProductService interface.
 */
@Service
public class ProductServiceImpl implements ProductService {

  private final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

  @Autowired
  ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository,
      EntityManager entityManager) {
    this.productRepository = productRepository;
    this.entityManager = entityManager;
  }

  @PersistenceContext
  private EntityManager entityManager;

  public ProductServiceImpl(ProductRepository productRepository) {
  }

  /**
   * Gets the products from the repository
   *
   * @param product - object that contains products
   * @return a List that contains all the products
   */
  public List<Product> getProducts(Product product) {
    try {
      return productRepository.findAll(Example.of(product));
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Retrieves all products from the database, optionally making use of an example if it is passed.
   *
   * @param pageSize - number of products on page
   * @param pageNo   - page number
   * @return - a map with all active products paginated
   */
  public Map<String, Object> findAllProducts(int pageNo, int pageSize) {
    List<Product> products = new ArrayList<Product>();
    Page<Product> pageCount = productRepository.findAllByActive(true,
        PageRequest.of(pageNo, pageSize));
    products = pageCount.getContent();
    Map<String, Object> response = new HashMap<>();
    response.put("products", products);
    response.put("totalPages", pageCount.getTotalPages());

    try {
      return response;
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());

    }
  }

  @Override
  public List<Product> getFilteredProducts(Product product) {
    return null;

  }

  /**
   * Retrieves the product with the provided id from the database.
   *
   * @param id - the id of the product to retrieve
   * @return - the product
   */
  public Product getProductById(Long id) {
    Product product;

    try {
      product = productRepository.findById(id).orElse(null);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }

    if (product != null) {
      return product;
    } else {
      logger.info("Get by id failed, it does not exist in the database: " + id);
      throw new ResourceNotFound("Get by id failed, it does not exist in the database: " + id);
    }
  }

  /**
   *  This method will delete a product from the database only if the ID of the product matches
   *  the provided ID
   * @param id - The ID of the product to be deleted from the database
   * @return
   */
  @Override
  public Long deleteProductById(Long id) {
    try {
      return productRepository.deleteProductById(id);
    } catch (Exception e) {
      throw new ServerError(e.getMessage());
    }
  }


  @Override
  public List<String> getProductByCategory() {
    try {
      return productRepository.getProductByCategory();
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  @Override
  public List<String> getProductTypes() {
    try {
      return productRepository.getProductByTypes();
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * get a list of top 4 viewed products
   *
   * @return top 4 viewed products
   */
  @Override
  public List<Product> getPopularProducts() {
    try {
      return productRepository.getPopularProducts();
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Insert and save a new Product to database.
   *
   * @param product saved product
   */
  @Override
  public Product saveProduct(Product product) {
    //if product is missing any codes, generate them
    generateMissingCodes(product);

    try {
      productRepository.save(product);
      logger.info("Saved product");
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
    return product;
  }

  /**
   * save updated products information
   *
   * @param updatedProducts - updated products to be saved
   * @return - the updated products
   */
  @Override
  public List<Product> updateProducts(List<Product> updatedProducts) {
    try {
      return productRepository.saveAll(updatedProducts);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Generates all missing required code fields for a product before persisting to database.
   *
   * @param product product to be checked for missing fields
   * @return product with all codes generated
   */
  private Product generateMissingCodes(Product product) {
    if (product.getPrimaryColorCode() == null ||
        product.getPrimaryColorCode().isEmpty()) {
      product.setPrimaryColorCode(ProductFactory.getColorCode());
    }
    if (product.getSecondaryColorCode() == null ||
        product.getSecondaryColorCode().isEmpty()) {
      product.setSecondaryColorCode(ProductFactory.getColorCode());
    }
    if (product.getStyleNumber() == null ||
        product.getStyleNumber().isEmpty()) {
      product.setStyleNumber(ProductFactory.getStyleCode());
    }
    if (product.getGlobalProductCode() == null ||
        product.getGlobalProductCode().isEmpty()) {
      product.setGlobalProductCode(ProductFactory.getRandomProductId());
    }

    return product;
  }
}