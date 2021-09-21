package io.catalyte.training.sportsproducts.data;

import static java.math.BigDecimal.valueOf;

import io.catalyte.training.sportsproducts.domains.rate.Rate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShippingRateLoader {

  List<Rate> buildShippingRates() {
    List<Rate> shippingRates = new ArrayList<>();

    shippingRates.add(writeShippingRate("shipping", "base", valueOf(5.00)));
    shippingRates.add(writeShippingRate("shipping", "extended", valueOf(10.00)));

    return shippingRates;
  }

  private Rate writeShippingRate(String type, String code, BigDecimal rate) {
    Rate shippingRate = new Rate();

    shippingRate.setType(type);
    shippingRate.setCode(code);
    shippingRate.setRate(rate);


    return shippingRate;
  }
}
