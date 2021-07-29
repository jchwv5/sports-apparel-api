package io.catalyte.training.sportsproducts.user;

import static io.catalyte.training.sportsproducts.constants.Paths.USERS_PATH;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public void registerNewUser(@RequestBody User user) {
    userServiceImpl.addNewUser(user);

  }

  @GetMapping(value = "/{id}/")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    logger.info("Request received for getUsersById: " + id);

    return new ResponseEntity<>(userServiceImpl.getUserById(id), HttpStatus.OK);
  }

}



