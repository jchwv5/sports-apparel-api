package io.catalyte.training.sportsproducts.domains.purchase;

import io.catalyte.training.sportsproducts.data.ProductFactory;
import io.catalyte.training.sportsproducts.domains.product.Product;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.web.server.ResponseStatusException;


public class PurchaseServiceImplTest {

  private final PurchaseServiceImpl testPurchaseValidation = new PurchaseServiceImpl(null, null, null);
  private final ProductFactory productFactory = new ProductFactory();
  List<Product> productList = productFactory.generateRandomProducts(4);

  Purchase purch1 = new Purchase();
  Purchase purch2 = new Purchase();
  Purchase purch3 = new Purchase();

  Product prod1 = productList.get(0);
  Product prod2 = productList.get(1);
  Product prod3 = productList.get(2);
  Product prod4 = productList.get(3);

  LineItem lineItem1 = new LineItem(101L, purch2, prod1, 5);
  LineItem lineItem2 = new LineItem(102L, purch2, prod2, 3);
  LineItem lineItem3 = new LineItem(103L, purch2, prod3, 1);
  LineItem lineItem4 = new LineItem(104L, purch3, prod4, 6);

  Set<LineItem> s1 = new HashSet<>();

  @Test
  public void checkForInactiveProductsEmptyPurchase() {
    purch1.setProducts(s1);
    Assertions.assertDoesNotThrow(()-> testPurchaseValidation.checkForInactiveProducts(purch1));
  }

  @Test
  public void checkForInactiveProductsAllProductsActive() {
    s1.add(lineItem1); s1.add(lineItem2); s1.add(lineItem3);
    purch2.setProducts(s1);
    prod1.setActive(true); prod2.setActive(true); prod3.setActive(true);
    Assertions.assertDoesNotThrow(()-> testPurchaseValidation.checkForInactiveProducts(purch2));
  }

  @Test
  public void checkForInactiveProductsAllProductsInactive() {
    s1.add(lineItem1); s1.add(lineItem2); s1.add(lineItem3);
    purch2.setProducts(s1);
    prod1.setActive(false); prod2.setActive(false); prod3.setActive(false);
    ResponseStatusException e1 = Assertions.assertThrows(ResponseStatusException.class, ()-> testPurchaseValidation.checkForInactiveProducts(purch2));
    assert(e1.getStatus().toString().equals("422 UNPROCESSABLE_ENTITY"));
  }

  @Test
  public void checkForInactiveProductsOneProductInactive() {
    s1.add(lineItem4);
    purch3.setProducts(s1);
    prod4.setActive(false);
    ResponseStatusException e1 = Assertions.assertThrows(ResponseStatusException.class, ()-> testPurchaseValidation.checkForInactiveProducts(purch3));
    assert(e1.getStatus().toString().equals("422 UNPROCESSABLE_ENTITY"));
  }

  @Test
  public void checkForInactiveProductsNotAllProductsInactive() {
    s1.add(lineItem1); s1.add(lineItem1); s1.add(lineItem3);
    purch2.setProducts(s1);
    prod1.setActive(true); prod2.setActive(false); prod3.setActive(false);
    ResponseStatusException e2 = Assertions.assertThrows(ResponseStatusException.class, ()-> testPurchaseValidation.checkForInactiveProducts(purch2));
    assert(e2.getStatus().toString().equals("422 UNPROCESSABLE_ENTITY"));
  }

}
