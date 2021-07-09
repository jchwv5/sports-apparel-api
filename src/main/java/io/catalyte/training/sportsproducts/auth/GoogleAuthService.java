package io.catalyte.training.sportsproducts.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.*;
import io.catalyte.training.sportsproducts.config.*;
import io.catalyte.training.sportsproducts.domains.user.*;
import java.io.*;
import java.security.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

@Component
public class GoogleAuthService {

  Logger logger = LogManager.getLogger(GoogleAuthService.class);
  String clientId = "912899852587-7996nh9mlpvpa2446q0il4f9hj5o492h.apps.googleusercontent.com";
  public GoogleAuthService() {};

  /**
   * Parses authorization header value and returns token
   *
   * @param bearerToken Authorization header value
   * @return String
   */
  public String getTokenFromHeader(String bearerToken) {

    // PARSE JWT TOKEN
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      logger.info("JWT Token successfully parsed");
      return bearerToken.substring(7);
    }

    logger.warn("JWT Token does not begin with the Bearer String");
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authorization Header must start with 'Bearer ' ");
  }

  /**
   * SEE DOCUMENTATION: https://developers.google.com/identity/sign-in/web/backend-auth
   *
   * @param idTokenString JWT Token
   * @param user          User making the request
   * @return User
   */
  public boolean authenticateUser(String idTokenString, User user) {

    // BUILD GOOGLE VERIFIER OBJECT
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
        new NetHttpTransport(),
        new GsonFactory()
    )
        .setAudience(Collections.singletonList(clientId))
        .build();

    // VERIFY TOKEN
    GoogleIdToken idToken;

    try {
      idToken = verifier.verify(idTokenString);
    } catch (GeneralSecurityException gse) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, gse.getMessage());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem reading the token");
    }

    if (idToken == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not verify token");
    }

    // GET USER FROM GOOGLE TOKEN
    // postman requests - create these.
    Payload payload = idToken.getPayload();

    // Google user accounts require email, first name, and last name. No error handling needed.
    String email = payload.getEmail();
    String firstName = (String) payload.get("given_name");
    String lastname = (String) payload.get("family_name");

    // CONSTRUCT USER
    User googleUser = new User();
    googleUser.setFirstName(firstName);
    googleUser.setLastName(lastname);
    googleUser.setEmail(email);

    // AUTHENTICATE USER
    return googleUser.getEmail().equals(user.getEmail());
  }
}
