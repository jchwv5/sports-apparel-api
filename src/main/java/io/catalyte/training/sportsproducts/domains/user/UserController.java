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
import org.springframework.web.bind.annotation.RequestBody;
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
  private UserServiceImpl userServiceImpl;

  @Autowired
  public UserController(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
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



