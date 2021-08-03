package io.catalyte.training.sportsproducts.domains.purchase;

import io.catalyte.training.sportsproducts.domains.product.Product;
import io.catalyte.training.sportsproducts.domains.product.ProductService;
import io.catalyte.training.sportsproducts.domains.purchase.LineItem;
import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class CheckForInactiveProductsTest {

  Purchase purch1 = new Purchase();
  Purchase purch2 = new Purchase();
  Purchase purch3 = new Purchase();

  Product prod1 = new Product("Colorful Hockey Visor", "This hockey visor for kids is colorful.", "Kids",
      "Hockey", "Visor", "2019-04-20");
  Product prod2 = new Product("Next Gen Weightlifting", "You're going to love it!", "Women", "Weightlifting",
      "Belt", "2020-07-22");
  Product prod3 = new Product("Skateboarding Jacket", "jacket for women", "Women",
      "Skateboarding", "Jacket", "2019-06-27");
  Product prod4 = new Product("Hockey Pool Noodle", "noodle for men", "Men",
       "Hockey","Pool Noodle","2017-03-01");

  LineItem lineItem1 = new LineItem(101L, purch1, prod1, 5);
  LineItem lineItem2 = new LineItem(102L, purch1, prod2, 3);
  LineItem lineItem3 = new LineItem(103L, purch1, prod3, 1);
  Set<LineItem> s1 = new HashSet<>();

  @Test
  public void checkForInactiveProductsEmptyPurchase() {
    purch1.setProducts(s1);
    Assertions.assertDoesNotThrow(()-> purch1.checkForInactiveProducts());
  }

  @Test
  public void checkForInactiveProductsAllProductsInactive() {
    s1.add(lineItem1); s1.add(lineItem2); s1.add(lineItem3);
    purch1.setProducts(s1);
    prod1.setActive(false); prod2.setActive(false); prod3.setActive(false);
    RuntimeException e = Assertions.assertThrows(RuntimeException.class, ()-> purch1.checkForInactiveProducts());
    assert(e.getMessage().contains("The following products in the purchase are inactive: Colorful Hockey Visor"+"\n"+"Skateboarding Jacket"+"\n"
        + "Next Gen Weightlifting"));
  }

  @Test
  public void checkForInactiveProductsNotAllProductsInactive() {
    s1.add(lineItem1); s1.add(lineItem2); s1.add(lineItem3);
    purch1.setProducts(s1);
    prod1.setActive(true); prod2.setActive(false); prod3.setActive(false);
    RuntimeException e = Assertions.assertThrows(RuntimeException.class, ()-> purch1.checkForInactiveProducts());
    assert(e.getMessage().contains("The following products in the purchase are inactive: Skateboarding Jacket"+"\n"
        + "Next Gen Weightlifting"));
  }
  
}
