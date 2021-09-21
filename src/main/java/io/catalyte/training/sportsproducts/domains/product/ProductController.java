package io.catalyte.training.sportsproducts.domains.product;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;

import com.google.api.client.util.Joiner;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The ProductController exposes endpoints for product related actions.
 */
@RestController
@RequestMapping(value = PRODUCTS_PATH)
public class ProductController {

  Logger logger = LogManager.getLogger(ProductController.class);

  private ProductService productService;
  private ProductRepository productRepository;

  @Autowired
  public ProductController(
      ProductService productService,
      ProductRepository productRepository) {
    this.productService = productService;
    this.productRepository = productRepository;
  }

  /**
   * Gets the paginated products by page No
   *
   * @param pageNo - the page number that needs to be found
   * @return a map that contain the pageNo that is needed and the total number of pages
   */
  @GetMapping(value = "/page/{pageNo}")
  public ResponseEntity<Map<String, Object>> getProductsPaginated(@PathVariable int pageNo) {
    logger.info("Request received for getProducts");
    int pageSize = 20;

    return new ResponseEntity<>(productService.findAllProducts(pageNo, pageSize), HttpStatus.OK);
  }

  /**
   * returns products meeting specification if search criteria is provided, or all products if no
   * search criteria is provided
   *
   * @param search  string of query elements for product specification
   * @param product example product
   * @return list of products meeting search criteria (if any) or all products
   */
  @GetMapping
  ResponseEntity<List<Product>> searchProducts(
      @RequestParam(value = "search", required = false) String search, Product product) {
    if (search != null) {
      search.replace("%20", " ");
      Specification<Product> spec = resolveSpecification(search);
      return new ResponseEntity<>(productRepository.findAll(spec), HttpStatus.OK);
    }
    return new ResponseEntity<>(productService.getProducts(product), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    logger.info("Request received for getProductsById: " + id);

    return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
  }

  @GetMapping(value = "/categories")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<List<String>> getCategories(Product category) {
    logger.info("Request received for getCategories...");

    return new ResponseEntity<>(productService.getProductByCategory(), HttpStatus.OK);
  }

  @GetMapping(value = "/types")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<List<String>> getTypes(Product type) {
    logger.info("Request received for getTypes...");

    return new ResponseEntity<>(productService.getProductTypes(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity saveProduct(@RequestBody Product product) {

    productService.saveProduct(product);

    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }

  /**
   * Resolves search parameters from restURL to construct query to db
   *
   * @param searchParameters
   * @return list of products matching search criteria
   */
  protected Specification<Product> resolveSpecification(String searchParameters) {

    ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
    String operationSetExper = Joiner.on('|')
        .join(Arrays.asList(SearchOperation.SIMPLE_OPERATION_SET));
    Pattern pattern = Pattern.compile(
        "(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
    Matcher matcher = pattern.matcher(searchParameters + ",");
    while (matcher.find()) {
      builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5),
          matcher.group(4), matcher.group(6));
    }
    return builder.build();
  }
}