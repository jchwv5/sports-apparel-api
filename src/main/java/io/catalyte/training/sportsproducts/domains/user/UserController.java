package io.catalyte.training.sportsproducts.domains.user;

import static io.catalyte.training.sportsproducts.constants.Paths.USERS_PATH;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The UserController exposes endpoints for user related actions.
 */
@RestController
@RequestMapping(value = USERS_PATH)
public class UserController {

  Logger logger = LogManager.getLogger(UserController.class);
  @Autowired
  private UserServiceImpl userServiceImpl;


  public UserController(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
  }

  // METHODS

  /**
   * Controller method for logging the user in
   *
   * @param user        User to login
   * @param bearerToken String value in the Authorization property of the header
   * @return User
   */
  @PostMapping(path = "/login")
  public ResponseEntity<User> loginUser(
      @RequestBody User user,
      @RequestHeader("Authorization") String bearerToken
  ) {
    logger.info("Request received for User Login");
    return new ResponseEntity<>(userServiceImpl.loginUser(bearerToken, user), HttpStatus.CREATED);
  }

  /**
   * Controller method for updating the user
   *
   * @param id          Id of the user to update
   * @param user        User to update
   * @param bearerToken String value in the Authorization property of the header
   * @return User - Updated user
   */
  @PutMapping(path = "/{id}")
  public ResponseEntity<User> updateUser(
      @PathVariable Long id,
      @RequestBody User user,
      @RequestHeader("Authorization") String bearerToken
  ) {
    logger.info("Request received for Update User");
    return new ResponseEntity<>(userServiceImpl.updateUser(bearerToken, id, user), HttpStatus.OK);
  }


  @PostMapping
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<User> addUserByEmail(@Valid @RequestBody User user) {
    logger.info("Request received for addUserByEmail: " + user.getEmail());
    return new ResponseEntity<>(userServiceImpl.addUserByEmail(user), HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    logger.info("Request received for getUsersById: " + id);
    return new ResponseEntity<>(userServiceImpl.getUserById(id), HttpStatus.OK);
  }

  /*
  This method handles validation and exception, making sure custom error message will display
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      logger.error("errorMessage {}", errorMessage);
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

}



