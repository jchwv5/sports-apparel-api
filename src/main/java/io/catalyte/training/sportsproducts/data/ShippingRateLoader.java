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
    shippingRates.add(writeShippingRate("tax", "Alabama", valueOf(4.000)));
    shippingRates.add(writeShippingRate("tax", "Alaska", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "American Samoa", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "Arizona", valueOf(5.600)));
    shippingRates.add(writeShippingRate("tax", "Arkansas", valueOf(6.500)));
    shippingRates.add(writeShippingRate("tax", "California", valueOf(7.250)));
    shippingRates.add(writeShippingRate("tax", "Colorado", valueOf(2.900)));
    shippingRates.add(writeShippingRate("tax", "Connecticut", valueOf(6.350)));
    shippingRates.add(writeShippingRate("tax", "Delaware", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "District of Columbia", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Federated States of Micronesia", valueOf(2.000)));
    shippingRates.add(writeShippingRate("tax", "Florida", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Georgia", valueOf(4.000)));
    shippingRates.add(writeShippingRate("tax", "Guam", valueOf(4.000)));
    shippingRates.add(writeShippingRate("tax", "Hawaii", valueOf(4.000)));
    shippingRates.add(writeShippingRate("tax", "Idaho", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Illinois", valueOf(6.250)));
    shippingRates.add(writeShippingRate("tax", "Indiana", valueOf(7.000)));
    shippingRates.add(writeShippingRate("tax", "Iowa", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Kansas", valueOf(6.500)));
    shippingRates.add(writeShippingRate("tax", "Kentucky", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Louisiana", valueOf(4.450)));
    shippingRates.add(writeShippingRate("tax", "Maine", valueOf(5.500)));
    shippingRates.add(writeShippingRate("tax", "Marshall Islands", valueOf(2.000)));
    shippingRates.add(writeShippingRate("tax", "Maryland", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Massachusetts", valueOf(6.250)));
    shippingRates.add(writeShippingRate("tax", "Michigan", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Minnesota", valueOf(6.875)));
    shippingRates.add(writeShippingRate("tax", "Mississippi", valueOf(7.000)));
    shippingRates.add(writeShippingRate("tax", "Missouri", valueOf(4.225)));
    shippingRates.add(writeShippingRate("tax", "Montana", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "Nebraska", valueOf(5.500)));
    shippingRates.add(writeShippingRate("tax", "Nevada", valueOf(6.850)));
    shippingRates.add(writeShippingRate("tax", "New Hampshire", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "New Jersey", valueOf(6.625)));
    shippingRates.add(writeShippingRate("tax", "New Mexico", valueOf(5.125)));
    shippingRates.add(writeShippingRate("tax", "New York", valueOf(4.000)));
    shippingRates.add(writeShippingRate("tax", "North Carolina", valueOf(4.750)));
    shippingRates.add(writeShippingRate("tax", "North Dakota", valueOf(5.000)));
    shippingRates.add(writeShippingRate("tax", "Northern Mariana Islands", valueOf(2.000)));
    shippingRates.add(writeShippingRate("tax", "Ohio", valueOf(5.750)));
    shippingRates.add(writeShippingRate("tax", "Oklahoma", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "Oregon", valueOf(4.500)));
    shippingRates.add(writeShippingRate("tax", "Palau", valueOf(2.000)));
    shippingRates.add(writeShippingRate("tax", "Pennsylvania", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Puerto Rico", valueOf(0.000)));
    shippingRates.add(writeShippingRate("tax", "Rhode Island", valueOf(7.000)));
    shippingRates.add(writeShippingRate("tax", "South Carolina", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "South Dakota", valueOf(4.500)));
    shippingRates.add(writeShippingRate("tax", "Tennessee", valueOf(7.000)));
    shippingRates.add(writeShippingRate("tax", "Texas", valueOf(6.250)));
    shippingRates.add(writeShippingRate("tax", "Utah", valueOf(4.850)));
    shippingRates.add(writeShippingRate("tax", "Vermont", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Virgin Island", valueOf(6.500)));
    shippingRates.add(writeShippingRate("tax", "Virginia", valueOf(4.300)));
    shippingRates.add(writeShippingRate("tax", "Washington", valueOf(6.500)));
    shippingRates.add(writeShippingRate("tax", "West Virginia", valueOf(6.000)));
    shippingRates.add(writeShippingRate("tax", "Wisconsin", valueOf(5.000)));
    shippingRates.add(writeShippingRate("tax", "Wyoming", valueOf(4.000)));

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
