package io.catalyte.training.sportsproducts.domains.purchase;

import java.util.Set;


/**
 * Describes a purchase object that holds the information for a transaction related to state change in UI
 */

public class PurchaseForTaxCalculation {

  private Set<LineItem> products;

  public String getDeliveryState() {
    return deliveryState;
  }

  public void setDeliveryState(String deliveryState) {
    this.deliveryState = deliveryState;
  }

  private String deliveryState;

  public Set<LineItem> getProducts() {
    return products;
  }

  public void setProducts(
      Set<LineItem> products) {
    this.products = products;
  }
}



