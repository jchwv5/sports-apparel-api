package io.catalyte.training.sportsproducts.domains.user;

import static io.catalyte.training.sportsproducts.constants.Roles.CUSTOMER;

import io.catalyte.training.sportsproducts.auth.*;
import io.catalyte.training.sportsproducts.exceptions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

/**
 * Implements user service interface
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private final UserRepository userRepository;

  private final GoogleAuthService googleAuthService = new GoogleAuthService();

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // METHODS

  /**
   * Updates a User given they are given the right credentials
   *
   * @param bearerToken String value in the Authorization property of the header
   * @param id          Id of the user to update
   * @param updatedUser User to update
   * @return User - Updated user
   */
  @Override
  public User updateUser(String bearerToken, Long id, User updatedUser) {

    // AUTHENTICATES USER - SAME EMAIL, SAME PERSON
    String token = googleAuthService.getTokenFromHeader(bearerToken);
    boolean isAuthenticated = googleAuthService.authenticateUser(token, updatedUser);

    if (!isAuthenticated) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Email in the request body does not match email from JWT Token");
    }

    // UPDATES USER
    User existingUser;

    try {
      existingUser = userRepository.findById(id).orElse(null);
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }

    if (existingUser == null) {
      throw new ResourceNotFound("User with id: " + id + " does not exist");
    }

    // TEMPORARY LOGIC TO PREVENT USER FROM UPDATING THEIR ROLE
    updatedUser.setRole(existingUser.getRole());

    // GIVE THE USER ID IF NOT SPECIFIED IN BODY TO AVOID DUPLICATE USERS
    if (updatedUser.getId() == null) {
      updatedUser.setId(id);
    }

    try {
      return userRepository.save(updatedUser);
    } catch (DataAccessException dae) {
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
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Email in the request body does not match email from JWT Token");
    }

    // SEE IF USER EXISTS
    User existingUser;

    try {
      existingUser = userRepository.findByEmail(user.getEmail());
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }

    // IF USER EXISTS, RETURN EXISTING USER
    if (existingUser != null) {
      return existingUser;
    }

    // ELSE CREATE NEW USER
    return createUser(user);
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
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must have an email field");
    }

    // CHECK TO MAKE SURE USER EMAIL IS NOT TAKEN
    User existingUser;

    try {
      existingUser = userRepository.findByEmail(user.getEmail());
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }

    if (existingUser != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is taken");
    }

    // SET DEFAULT ROLE TO CUSTOMER
    // NOT RUNNING CONDITIONAL DUE TO SOMEONE ASSIGNING THEMSELVES A ROLE
    // if (user.getRole() == null) {
    user.setRole(CUSTOMER);
    // }

    // SAVE USER
    try {
      return userRepository.save(user);
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }

  }

}
