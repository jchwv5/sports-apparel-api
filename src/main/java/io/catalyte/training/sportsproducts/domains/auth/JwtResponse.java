package io.catalyte.training.sportsproducts.domains.auth;

import io.catalyte.training.sportsproducts.domains.user.*;
import java.io.*;

public class JwtResponse implements Serializable {

  private static final Long serialVersionUID = -8091879091924046844L;
  private String jwtToken;

  public JwtResponse() {}

  public JwtResponse(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public JwtResponse(String jwtToken, User user) {
    this.jwtToken = jwtToken;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }
}
