package io.catalyte.training.sportsproducts.domains.user;

import static io.catalyte.training.sportsproducts.constants.Roles.CUSTOMER;

import io.catalyte.training.sportsproducts.auth.GoogleAuthService;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 * This class provides the implementation for the UserService interface. =======
 */

@Service
public class UserServiceImpl implements UserService {

  private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

  @Autowired
  private final UserRepository userRepository;
  private final GoogleAuthService googleAuthService = new GoogleAuthService();
  private final UserValidation userValidation = new UserValidation();

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieves the user with the provided id from the database.
   *
   * @param id - the id of the user to retrieve
   * @return - the user
   */
  public User getUserById(Long id) {
    User user;
    try {
      user = userRepository.findById(id).orElse(null);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
    if (user != null) {
      return user;
    } else {
      logger.info("Get by id failed, id does not exist in the database: " + id);
      throw new ResourceNotFound(
          "Get by id failed. id " + id + " does not exist in the database: ");
    }
  }

  /**
   * Retrieves the user info with the matching email from the database.
   * @param email - user email to find
   * @return user
   */
  public User findUserByEmail(String email) {
    User user;
    try {
      user = userRepository.findByEmail(email);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
    if (user != null) {
      return user;
    } else {
      logger.info("Get by email failed, email does not exist in the database" + email);
      throw new ResourceNotFound(
          "Get by email failed. email " + email + "does not exist in the database: "
      );
    }
  }

  /**
   * Adds a new user with unique email to the database.
   *
   * @param user - the user
   * @return - the user
   */
  public User addUserByEmail(User user) {
    userValidation.validateUser(user);
    Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
    if (userOptional.isPresent()) {
      logger.info("Add new user failed, email already exists in the database: " + user.getEmail());
      throw new IllegalStateException("Email already exists: " + user.getEmail());
    }
    userRepository.save(user);
    System.out.println(user);
    return user;
  }

  /**
   * Updates a User given they are given the right credentials
   *
   * @param bearerToken - String value in the Authorization property of the header
   * @param id          - id of the user to update
   * @param updatedUser - User to update
   * @return User - Updated user
   */
  @Override
  public User updateUser(String bearerToken, Long id, User updatedUser) {

    // AUTHENTICATES USER - SAME EMAIL, SAME PERSON
    String token = googleAuthService.getTokenFromHeader(bearerToken);
    boolean isAuthenticated = googleAuthService.authenticateUser(token, updatedUser);

    if (!isAuthenticated) {
      logger.error("Email in the request body does not match email from JWT");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Email in the request body does not match email from JWT Token");
    }

    // UPDATES USER
    User existingUser;

    try {
      existingUser = userRepository.findById(id).orElse(null);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    if (existingUser == null) {
      logger.error("User with id: " + id + " does not exist");
      throw new ResourceNotFound("User with id: " + id + " does not exist");
    }

    // TEMPORARY LOGIC TO PREVENT USER FROM UPDATING THEIR ROLE
    updatedUser.setRole(existingUser.getRole());

    // GIVE THE USER ID IF NOT SPECIFIED IN BODY TO AVOID DUPLICATE USERS
    if (updatedUser.getId() == null) {
      updatedUser.setId(id);
    }

    try {
      logger.info("Saved user to");
      return userRepository.save(updatedUser);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

  }

  /**
   * Logs user in with Google Auth - Returns existing user information or creates a new user
   *
   * @param bearerToken String value in the Authorization property of the header
   * @param user        User to login
   * @return User
   */
  @Override
  public User loginUser(String bearerToken, User user) {

    // AUTHENTICATES USER
    // Authenticating the user ensures that the user is using Google to sign in
    String token = googleAuthService.getTokenFromHeader(bearerToken);
    boolean isAuthenticated = googleAuthService.authenticateUser(token, user);

    if (!isAuthenticated) {
      logger.error("Email in the request body does not match email from JWT");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Email in the request body does not match email from JWT Token");
    }

    // SEE IF USER EXISTS
    User existingUser;

    //create a clock
    ZoneId zid = ZoneId.of("UTC");
    // create an LocalDateTime object using now(zoneId)
    LocalDateTime lt = LocalDateTime.now(zid);
    try {
      existingUser = userRepository.findByEmail(user.getEmail());
    } catch (DataAccessException dae) {
      logger.info(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    // IF USER EXISTS, RETURN EXISTING USER WITH UPDATED TIMESTAMP
    if (existingUser != null) {
      logger.info("Existing user has been found");
      existingUser.setLastActiveTime(lt);
      userRepository.save(existingUser);
      return existingUser;
    }

    // ELSE CREATE NEW USER WITHOUT VALIDATION
    user.setLastActiveTime(lt);
    return createUserWithoutValidation(user);
  }

  /**
   * Creates a user in the database, given email is not null and not taken
   *
   * @param user User to create
   * @return User
   */
  @Override
  public User createUser(User user) {

    String email = user.getEmail();

    // CHECK TO MAKE SURE EMAIL EXISTS ON INCOMING USER
    if (email == null) {
      logger.error("User must have an email field");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must have an email field");
    }

    // VALIDATE THE USER
    userValidation.validateUser(user);

    // CHECK TO MAKE SURE USER EMAIL IS NOT TAKEN
    User existingUser;

    try {
      existingUser = userRepository.findByEmail(user.getEmail());
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    if (existingUser != null) {
      logger.error("Email is taken");
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is taken");
    }

    // SET DEFAULT ROLE TO CUSTOMER
    user.setRole(CUSTOMER);

    // SAVE USER
    try {
      logger.info("Saving user");
      return userRepository.save(user);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

  }

  /**
   * Creates a user without Validation
   *
   * @param user User to create
   * @return Saved User
   */
  @Override
  public User createUserWithoutValidation(User user) {
    String email = user.getEmail();

    // CHECK TO MAKE SURE EMAIL EXISTS ON INCOMING USER
    if (email == null) {
      logger.error("User must have an email field");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must have an email field");
    }

    // CHECK TO MAKE SURE USER EMAIL IS NOT TAKEN
    User existingUser;

    try {
      existingUser = userRepository.findByEmail(user.getEmail());
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    if (existingUser != null) {
      logger.error("Email is taken");
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is taken");
    }

    // SET DEFAULT ROLE TO CUSTOMER
    user.setRole(CUSTOMER);

    // SAVE USER
    try {
      logger.info("Saving user");
      return userRepository.save(user);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

  }


}
