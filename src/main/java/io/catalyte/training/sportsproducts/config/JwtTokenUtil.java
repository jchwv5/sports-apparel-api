package io.catalyte.training.sportsproducts.config;

import io.catalyte.training.sportsproducts.domains.user.*;
import io.jsonwebtoken.*;
import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;

@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = -2550185165626007488L;

  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

  @Autowired
  private Environment env;

  String secret = "secret";

  /**
   * Gets username from token
   *
   * @param token String - JWT Token
   * @return Claim
   */
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * Retrieve expiration date from token
   *
   * @param token String
   * @return Claims
   */
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * Gets claims from token
   *
   * @param token          JWT Token
   * @param claimsResolver Function
   * @param <T>            Generic
   * @return Claim
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Generates all claims from token
   *
   * @param token Token
   * @return Claims
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }


  /**
   * Checks if token is expired
   *
   * @param token Token
   * @return Boolean
   */
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * Generates a token for a user
   *
   * @param user User
   * @return String - JWT Token
   */
  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    return generateToken(claims, user.getEmail());
  }

  /**
   * Generates a token
   *
   * @param claims  Claims
   * @param subject Subject
   * @return token
   * @implNote https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1
   */
  private String generateToken(Map<String, Object> claims, String subject) {



    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  /**
   * Validates token
   *
   * @param token Token
   * @param user  User
   * @return Boolean
   */
  public Boolean validateToken(String token, User user) {
    final String username = getUsernameFromToken(token);
    return (username.equals(user.getEmail()) && !isTokenExpired(token));
  }
}
