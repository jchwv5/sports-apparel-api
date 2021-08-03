package io.catalyte.training.sportsproducts.domains.purchase;

import io.catalyte.training.sportsproducts.domains.product.Product;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.web.server.ResponseStatusException;


public class CheckForInactiveProductsTest {

  Purchase purch1 = new Purchase();
  Purchase purch2 = new Purchase();
  Purchase purch3 = new Purchase();
  Purchase purch4 = new Purchase();

  Product prod1 = new Product("Colorful Hockey Visor", "This hockey visor for kids is colorful.", "Kids",
      "Hockey", "Visor", "2019-04-20");
  Product prod2 = new Product("Next Gen Weightlifting", "You're going to love it!", "Women", "Weightlifting",
      "Belt", "2020-07-22");
  Product prod3 = new Product("Skateboarding Jacket", "jacket for women", "Women",
      "Skateboarding", "Jacket", "2019-06-27");
  Product prod4 = new Product("", "noodle for men", "Men",
      "Hockey","Pool Noodle","2017-03-01");

  LineItem lineItem1 = new LineItem(101L, purch2, prod1, 5);
  LineItem lineItem2 = new LineItem(102L, purch2, prod2, 3);
  LineItem lineItem3 = new LineItem(103L, purch2, prod3, 1);
  LineItem lineItem4 = new LineItem(104L, purch3, prod4, 6);
  LineItem lineItem5 = new LineItem(105L, purch3, prod1, 6);
  LineItem lineItem6 = new LineItem(106L, purch3, prod2, 6);
  LineItem lineItem7 = new LineItem(107L, purch4, prod1, 5);
  Set<LineItem> s1 = new HashSet<>();

  @Test
  public void checkForInactiveProductsEmptyPurchase() {
    purch1.setProducts(s1);
    Assertions.assertDoesNotThrow(()-> purch1.checkForInactiveProducts());
  }

  @Test
  public void checkForInactiveProductsOneProductInactive() {
    s1.add(lineItem7);
    purch4.setProducts(s1);
    prod1.setActive(false);
    ResponseStatusException e1 = Assertions.assertThrows(ResponseStatusException.class, ()-> purch4.checkForInactiveProducts());
    assert(e1.getMessage().contains("The following products in the purchase are inactive: Colorful Hockey Visor"));
    assert(e1.getStatus().toString().equals("422 UNPROCESSABLE_ENTITY"));
  }

  @Test
  public void checkForInactiveProductsAllProductsInactive() {
    s1.add(lineItem1); s1.add(lineItem2); s1.add(lineItem3);
    purch2.setProducts(s1);
    prod1.setActive(false); prod2.setActive(false); prod3.setActive(false);
    ResponseStatusException e1 = Assertions.assertThrows(ResponseStatusException.class, ()-> purch2.checkForInactiveProducts());
    assert(e1.getMessage().contains("The following products in the purchase are inactive: "));
    assert(e1.getStatus().toString().equals("422 UNPROCESSABLE_ENTITY"));
  }

  @Test
  public void checkForInactiveProductsNotAllProductsInactive() {
    s1.add(lineItem4); s1.add(lineItem5); s1.add(lineItem6);
    purch3.setProducts(s1);
    prod4.setActive(true); prod1.setActive(false); prod2.setActive(false);
    ResponseStatusException e2 = Assertions.assertThrows(ResponseStatusException.class, ()-> purch3.checkForInactiveProducts());
    assert(e2.getMessage().contains("The following products in the purchase are inactive: "));
    assert(e2.getStatus().toString().equals("422 UNPROCESSABLE_ENTITY"));
  }

}
