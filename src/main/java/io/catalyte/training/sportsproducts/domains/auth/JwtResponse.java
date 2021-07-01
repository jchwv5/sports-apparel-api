package io.catalyte.training.sportsproducts.domains.auth;

import java.io.*;

public class JwtResponse implements Serializable {

  private static final Long serialVersionUID = -8091879091924046844L;
  private final String jwtToken;

  public JwtResponse(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public String getToken() {
    return this.jwtToken;
  }

}
