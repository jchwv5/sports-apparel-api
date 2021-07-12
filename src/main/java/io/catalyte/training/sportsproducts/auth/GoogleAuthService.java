package io.catalyte.training.sportsproducts.auth;

import static io.catalyte.training.sportsproducts.constants.StringConstants.GOOGLE_CLIENT_ID;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.*;
import io.catalyte.training.sportsproducts.domains.user.*;
import java.security.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

@Component
public class GoogleAuthService {

  Logger logger = LogManager.getLogger(GoogleAuthService.class);
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

    logger.error("JWT Token does not begin with the Bearer String");
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
        .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
        .build();

    GoogleIdToken idToken;

    // VERIFY TOKEN
    try {
      idToken = verifier.verify(idTokenString);
      logger.info("Verified token");
    } catch (GeneralSecurityException gse) {
      logger.error(gse.getMessage());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, gse.getMessage());
    } catch (Exception e) {
      logger.error("There was a problem reading the token");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was a problem reading the token");
    }

    if (idToken == null) {
      logger.error("Could not verify token");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not verify token");
    }

    // GET EMAIL FROM GOOGLE TOKEN
    String googleEmail = idToken.getPayload().getEmail();

    // AUTHENTICATE USER
    return googleEmail.equals(user.getEmail());
  }
}
