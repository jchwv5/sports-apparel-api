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

  @PutMapping(path = "/{id}")
  public ResponseEntity<User> updateUser(
      @PathVariable Long id,
      @RequestBody User user,
      @RequestHeader("Authorization") String authorization
  ) {
    logger.info("Request received for Update User");
    return new ResponseEntity<>(userService.updateUser(authorization, id, user), HttpStatus.OK);
  }

  @PostMapping(path = "/login")
  public ResponseEntity<JwtResponse> loginUser(
      @RequestBody User user
  ) {
    logger.info("Request received for User Login");
    return new ResponseEntity<>(userService.loginUser(user), HttpStatus.OK);
  }

}
