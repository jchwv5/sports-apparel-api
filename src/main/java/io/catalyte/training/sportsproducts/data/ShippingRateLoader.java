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
    shippingRates.add(writeShippingRate("shipping", "additional", valueOf(15.00)));
    shippingRates.add(writeShippingRate("tax", "Alabama", valueOf(0.0400)));
    shippingRates.add(writeShippingRate("tax", "Alaska", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "American Samoa", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "Arizona", valueOf(0.056)));
    shippingRates.add(writeShippingRate("tax", "Arkansas", valueOf(0.065)));
    shippingRates.add(writeShippingRate("tax", "California", valueOf(0.0725)));
    shippingRates.add(writeShippingRate("tax", "Colorado", valueOf(0.029)));
    shippingRates.add(writeShippingRate("tax", "Connecticut", valueOf(0.0635)));
    shippingRates.add(writeShippingRate("tax", "Delaware", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "District of Columbia", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Federated States of Micronesia", valueOf(0.020)));
    shippingRates.add(writeShippingRate("tax", "Florida", valueOf(0.060)));
    shippingRates.add(writeShippingRate("tax", "Georgia", valueOf(0.04)));
    shippingRates.add(writeShippingRate("tax", "Guam", valueOf(0.04)));
    shippingRates.add(writeShippingRate("tax", "Hawaii", valueOf(0.04)));
    shippingRates.add(writeShippingRate("tax", "Idaho", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Illinois", valueOf(0.0625)));
    shippingRates.add(writeShippingRate("tax", "Indiana", valueOf(0.07)));
    shippingRates.add(writeShippingRate("tax", "Iowa", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Kansas", valueOf(0.0650)));
    shippingRates.add(writeShippingRate("tax", "Kentucky", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Louisiana", valueOf(0.0445)));
    shippingRates.add(writeShippingRate("tax", "Maine", valueOf(0.055)));
    shippingRates.add(writeShippingRate("tax", "Marshall Islands", valueOf(0.02)));
    shippingRates.add(writeShippingRate("tax", "Maryland", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Massachusetts", valueOf(0.0625)));
    shippingRates.add(writeShippingRate("tax", "Michigan", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Minnesota", valueOf(0.06875)));
    shippingRates.add(writeShippingRate("tax", "Mississippi", valueOf(0.07)));
    shippingRates.add(writeShippingRate("tax", "Missouri", valueOf(0.04225)));
    shippingRates.add(writeShippingRate("tax", "Montana", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "Nebraska", valueOf(0.055)));
    shippingRates.add(writeShippingRate("tax", "Nevada", valueOf(0.685)));
    shippingRates.add(writeShippingRate("tax", "New Hampshire", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "New Jersey", valueOf(0.06625)));
    shippingRates.add(writeShippingRate("tax", "New Mexico", valueOf(0.05125)));
    shippingRates.add(writeShippingRate("tax", "New York", valueOf(0.04)));
    shippingRates.add(writeShippingRate("tax", "North Carolina", valueOf(0.04750)));
    shippingRates.add(writeShippingRate("tax", "North Dakota", valueOf(0.055)));
    shippingRates.add(writeShippingRate("tax", "Northern Mariana Islands", valueOf(0.02)));
    shippingRates.add(writeShippingRate("tax", "Ohio", valueOf(0.0575)));
    shippingRates.add(writeShippingRate("tax", "Oklahoma", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "Oregon", valueOf(0.045)));
    shippingRates.add(writeShippingRate("tax", "Palau", valueOf(0.02)));
    shippingRates.add(writeShippingRate("tax", "Pennsylvania", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Puerto Rico", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "Rhode Island", valueOf(0.07)));
    shippingRates.add(writeShippingRate("tax", "South Carolina", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "South Dakota", valueOf(0.045)));
    shippingRates.add(writeShippingRate("tax", "Tennessee", valueOf(0.07)));
    shippingRates.add(writeShippingRate("tax", "Texas", valueOf(0.0625)));
    shippingRates.add(writeShippingRate("tax", "Utah", valueOf(0.0485)));
    shippingRates.add(writeShippingRate("tax", "Vermont", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Virgin Island", valueOf(0.065)));
    shippingRates.add(writeShippingRate("tax", "Virginia", valueOf(0.043)));
    shippingRates.add(writeShippingRate("tax", "Washington", valueOf(0.065)));
    shippingRates.add(writeShippingRate("tax", "West Virginia", valueOf(0.06)));
    shippingRates.add(writeShippingRate("tax", "Wisconsin", valueOf(0.050)));
    shippingRates.add(writeShippingRate("tax", "Wyoming", valueOf(0.04)));

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
