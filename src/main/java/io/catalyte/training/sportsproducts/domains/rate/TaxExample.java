package io.catalyte.training.sportsproducts.domains.rate;

import java.math.BigDecimal;
import java.util.HashMap;

public class TaxExample {

  static HashMap<String, String> map = new HashMap<>();

  {
    map.put("AL", "4.000%");
    map.put("AK", "0.000%");
    map.put("AS", "0.000%");
    map.put("AZ", "5.600%");
    map.put("AR", "6.500%");
    map.put("CA", "7.250%");
    map.put("CO", "2.900%");
    map.put("CT", "6.350%");
    map.put("DE", "0.000%");
    map.put("DC", "6.000%");
    map.put("FL", "6.000%");
    map.put("GA", "4.000%");
    map.put("GU", "4.000%");
    map.put("HI", "4.000%");
    map.put("ID", "6.000%");
    map.put("IL", "6.250%");
    map.put("IN", "7.000%");
    map.put("IA", "6.000%");
    map.put("KS", "6.500%");
    map.put("KY", "6.000%");
    map.put("LA", "4.450%");
    map.put("ME", "5.500%");
    map.put("MD", "6.000%");
    map.put("MA", "6.250%");
    map.put("MI", "6.000%");
    map.put("MN", "6.875%");
    map.put("MS", "7.000%");
    map.put("MO", "4.225%");
    map.put("MT", "0.000%");
    map.put("NE", "5.50%");
    map.put("NV", "6.85%");
    map.put("NH", "0.000%");
    map.put("NJ", "6.625%");
    map.put("NM", "5.125%");
    map.put("NY", "4.000%");
    map.put("NC", "4.750%");
    map.put("ND", "5.000%");
    map.put("MP", "5.750%");
    map.put("OH", "5.750%");
    map.put("OK", "4.500%");
    map.put("OR", "0.000%");
    map.put("PA", "6.000%");
    map.put("PR", "11.500%");
    map.put("RI", "7.000%");
    map.put("SC", "6.000%");
    map.put("SD", "4.500%");
    map.put("TN", "7.000%");
    map.put("TX", "6.250%");
    map.put("UT", "4.850%");
    map.put("VT", "6.000%");
    map.put("VA", "4.300%");
    map.put("VI", "6.500%");
    map.put("WA", "6.500%");
    map.put("WV", "6.000%");
    map.put("WI", "5.000%");
    map.put("WY", "4.000%");
    map.put("PW", "4.000%");
    map.put("MH", "4.000%");
    map.put("FM", "4.000%");
  }

  public static BigDecimal getTaxBystate(String key) {
    BigDecimal tax = null;
    if (map.containsKey(key)) {
      tax = new BigDecimal(map.get(key));
    }

    return tax;

  }
}
