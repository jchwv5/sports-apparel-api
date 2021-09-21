package io.catalyte.training.sportsproducts.domains.purchase;

import java.util.HashMap;

public class StateAbbreviation {
  static HashMap<String, String> map = new HashMap<>();
  //static block to initialize map key value pairs when the class loads
  {
    map.put("ALABAMA", "AL");
    map.put("ALASKA", "AK");
    map.put("AMERICAN SAMOA", "AS");
    map.put("ARIZONA", "AZ");
    map.put("ARKANSAS", "AR");
    map.put("CALIFORNIA", "CA");
    map.put("COLORADO", "CO");
    map.put("CONNECTICUT", "CT");
    map.put("DELAWARE", "DE");
    map.put("DISTRICT OF COLUMBIA", "DC");
    map.put("FLORIDA", "FL");
    map.put("GEORGIA", "GA");
    map.put("GUAM", "GU");
    map.put("HAWAII", "HI");
    map.put("IDAHO", "ID");
    map.put("ILLINOIS", "IL");
    map.put("INDIANA", "IN");
    map.put("IOWA", "IA");
    map.put("KANSAS", "KS");
    map.put("KENTUCKY", "KY");
    map.put("LOUISIANA", "LA");
    map.put("MAINE", "ME");
    map.put("MARYLAND", "MD");
    map.put("MASSACHUSETTS", "MA");
    map.put("MICHIGAN", "MI");
    map.put("MINNESOTA", "MN");
    map.put("MISSISSIPPI", "MS");
    map.put("MISSOURI", "MO");
    map.put("MONTANA", "MT");
    map.put("NEBRASKA", "NE");
    map.put("NEVADA", "NV");
    map.put("NEW HAMPSHIRE", "NH");
    map.put("NEW JERSEY", "NJ");
    map.put("NEW MEXICO", "NM");
    map.put("NEW YORK", "NY");
    map.put("NORTH CAROLINA", "NC");
    map.put("NORTH DAKOTA", "ND");
    map.put("NORTHERN MARIANA ISLANDS", "MP");
    map.put("OHIO", "OH");
    map.put("OKLAHOMA", "OK");
    map.put("OREGON", "OR");
    map.put("PENNSYLVANIA", "PA");
    map.put("PUERTO RICO", "PR");
    map.put("RHODE ISLAND", "RI");
    map.put("SOUTH CAROLINA", "SC");
    map.put("SOUTH DAKOTA", "SD");
    map.put("TENNESSEE", "TN");
    map.put("TEXAS", "TX");
    map.put("UTAH", "UT");
    map.put("VERMONT", "VT");
    map.put("VIRGINIA", "VA");
    map.put("VIRGIN ISLANDS", "VI");
    map.put("WASHINGTON", "WA");
    map.put("WEST VIRGINIA", "WV");
    map.put("WISCONSIN", "WI");
    map.put("WYOMING", "WY");
    map.put("PALAU", "PW");
    map.put("MARSHALL ISLANDS", "MH");
    map.put("FEDERATED STATES OF MICRONESIA", "FM");
  }

  /**
   *
   * @param key - key of the map, US states
   * @return - a string, the two-letter abbreviation of the state
   */
  public static String convertStateAbbreviations(String key){
       String state = null;
        if( map.containsKey(key)){
         state = map.get(key);
      }
        return state;

  }

}
