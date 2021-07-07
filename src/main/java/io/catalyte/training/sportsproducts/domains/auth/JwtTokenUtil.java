package io.catalyte.training.sportsproducts.domains.auth;

import io.catalyte.training.sportsproducts.domains.user.*;
import io.jsonwebtoken.*;
import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;

@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = -2550185165626007488L;

  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//  public static final long JWT_TOKEN_VALIDITY = 10; // TEST CASE

  Logger logger = LogManager.getLogger(JwtTokenUtil.class);

  @Autowired
  private Environment env;

  String secret = "secret";

  public String getTokenFromAuthorization(String authorization) {

    // PARSE JWT TOKEN
    if (authorization != null && authorization.startsWith("Bearer ")) {
      return authorization.substring(7);
    }

    logger.warn("JWT Token does not being with the Bearer String");
    return null;
  }

  /**
   * Gets username from token
   *
   * @param token String - JWT Token
   * @return Claim
   */
  public String getEmailFromToken(String token) {
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
    return Jwts
        .parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
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
  public String generateJwtToken(User user) {
   // CREATE CLAIMS BODY
    Map<String, Object> claims = new HashMap<>();
    claims.put("user", user);

    // GENERATE THE JWT
    return generateJwt(claims, user.getEmail());
  }

  /**
   * Generates a token
   *
   * @param claims  Claims
   * @param subject Subject
   * @return token
   * @implNote https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1
   */
  private String generateJwt(Map<String, Object> claims, String subject) {
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
    // GET EMAIL FROM TOKEN
    final String email = getEmailFromToken(token);

    // CHECK IF TOKEN EMAIL AND EXPIRATION ARE VALID
    boolean isValidEmail = email.equals(user.getEmail());
    boolean isValidToken = !isTokenExpired(token);

    // RETURN VALIDITY
    return (isValidToken && isValidEmail);
  }
}
