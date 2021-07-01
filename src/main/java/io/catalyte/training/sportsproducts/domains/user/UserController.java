package io.catalyte.training.sportsproducts.domains.user;

import static io.catalyte.training.sportsproducts.constants.Paths.USERS_PATH;

import io.catalyte.training.sportsproducts.domains.auth.*;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = USERS_PATH)
public class UserController {

  Logger logger = LogManager.getLogger(UserController.class);

  @Autowired
  private final UserServiceImpl userService;

  public UserController(
      UserServiceImpl userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<User> createUser(
      @RequestBody User user
  ) {
    logger.info("Request received for Create User");
    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<User> updateUser(
      @RequestBody User user
  ) {
    logger.info("Request received for Update User");
    return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
  }

  @PostMapping(path = "/login")
  public ResponseEntity<JwtResponse> loginUser(
      @RequestBody User user
  ) {
    logger.info("Request received for User Login");
    return new ResponseEntity<>(userService.loginUser(user), HttpStatus.OK);
  }

}
