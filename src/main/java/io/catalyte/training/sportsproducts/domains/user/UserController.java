package io.catalyte.training.sportsproducts.domains.user;

import static io.catalyte.training.sportsproducts.constants.Paths.USERS_PATH;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = USERS_PATH)
public class UserController {

  @Autowired
  private final UserServiceImpl userService;

  public UserController(
      UserServiceImpl userService) {
    this.userService = userService;
  }

  @PostMapping(path = "/authorize")
  public ResponseEntity<User> authorizeUser(
      @RequestBody User user
  ) {
    return new ResponseEntity<>(userService.authorizeUser(user), HttpStatus.CREATED);
  }

}
