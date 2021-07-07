package io.catalyte.training.sportsproducts.domains.auth;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException exception)
      throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
