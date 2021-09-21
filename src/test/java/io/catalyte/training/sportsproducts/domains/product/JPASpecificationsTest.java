package io.catalyte.training.sportsproducts.domains.product;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

@RunWith(MockitoJUnitRunner.class)
public class JPASpecificationsTest {

  @Mock
  private ProductRepository productRepository;

  private Product testProductOne;

  private Product testProductTwo;

  private List<Product> productList = new ArrayList<>();

  @Before
  public void setUp() throws Exception {

    testProductOne = new Product();
    testProductOne.setName("Test Product One");
    testProductOne.setDescription("This is a test product");
    testProductOne.setDemographic("Men");
    testProductOne.setCategory("Hockey");
    testProductOne.setType("Short");
    testProductOne.setReleaseDate(LocalDate.of(2021, 1, 1));
    testProductOne.setPrimaryColorCode("#f9845b");
    testProductOne.setSecondaryColorCode("#51b46d");
    testProductOne.setStyleNumber("sc26955");
    testProductOne.setGlobalProductCode("po-1331144");
    testProductOne.setBrand("Nike");
    testProductOne.setMaterial("Cotton");
    testProductOne.setPrice(BigDecimal.valueOf(100.00));
    testProductOne.setQuantity(10);
    testProductOne.setImageSrc("https://m.media-amazon.com/images/I/81blOUiYdaL._AC_SX679_.jpg");
    testProductOne.setActive(true);
    productList.add(testProductOne);

    testProductTwo = new Product();
    testProductTwo.setName("Test Product Two");
    testProductTwo.setDescription("This is a test product");
    testProductTwo.setDemographic("Women");
    testProductTwo.setCategory("Golf");
    testProductTwo.setType("Hat");
    testProductTwo.setReleaseDate(LocalDate.of(2021, 1, 1));
    testProductTwo.setPrimaryColorCode("#f9845b");
    testProductTwo.setSecondaryColorCode("#51b46d");
    testProductTwo.setStyleNumber("sc26955");
    testProductTwo.setGlobalProductCode("po-1331144");
    testProductTwo.setBrand("Adidas");
    testProductTwo.setMaterial("Silicone");
    testProductTwo.setPrice(BigDecimal.valueOf(99.99));
    testProductTwo.setQuantity(10);
    testProductTwo.setImageSrc("https://m.media-amazon.com/images/I/81blOUiYdaL._AC_SX679_.jpg");
    testProductTwo.setActive(true);
    productList.add(testProductTwo);

    MockitoAnnotations.initMocks(this);

    when(productRepository.findAll(Mockito.any(Specification.class))).thenReturn(productList);

  }

  @Test
  public void testEquality() {
    ProductSpecification spec =
        new ProductSpecification(new SpecSearchCriteria("brand", SearchOperation.EQUALITY, "Nike"));

    List<Product> results = productRepository.findAll(spec);

   assert(results.contains(testProductOne));
  }

}
