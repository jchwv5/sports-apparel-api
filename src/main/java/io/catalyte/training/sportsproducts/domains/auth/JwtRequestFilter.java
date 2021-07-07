package io.catalyte.training.sportsproducts.domains.auth;

import io.catalyte.training.sportsproducts.domains.user.*;
import io.jsonwebtoken.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.context.*;
import org.springframework.web.filter.*;

public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  )
      throws ServletException, IOException {

    String requestTokenHeader = request.getHeader("Authorization");
    String email = null;
    String jwtToken = null;

    // PARSE JWT TOKEN
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        email = jwtTokenUtil.getEmailFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        throw new JwtException("JWT Token has expired");
      }
    }

    if (
        email != null &&
            SecurityContextHolder
                .getContext()
                .getAuthentication() == null
    ) {

      User user = userRepository.findByEmail(email);
      
      if (!jwtTokenUtil.validateToken(jwtToken, user)) {
        throw new JwtException("Invalid token");
      }

    }
    filterChain.doFilter(request, response);
  }

}
