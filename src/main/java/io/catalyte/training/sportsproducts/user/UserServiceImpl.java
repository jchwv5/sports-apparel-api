package io.catalyte.training.sportsproducts.user;


import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


/**
 * This class provides the implementation for the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {

  private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieves all user from the database, optionally making use of an example if it is passed.
   *
   * @param user - an example user to use for querying
   * @return - a list of users matching the example, or all users if no example was passed
   */
  public List<User> getUsers(User user) {
    try {
      return userRepository.findAll(Example.of(user));
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
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
      logger.info("Get by id failed, it does not exist in the database: " + id);
      throw new ResourceNotFound("Get by id failed, it does not exist in the database: " + id);
    }
  }

  public User addUserByEmail(User user) {
    Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
    if (userOptional.isPresent()) {
      logger
          .info("Add new user by id failed, it already exists in the database: " + user.getEmail());
      throw new IllegalStateException("email taken");
    }
    userRepository.save(user);
    System.out.println(user);
    return user;
  }

  public void deleteUserById(Long id) {
    User user;
    try {
      user = userRepository.findById(id).orElse(null);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
    if (user == null) {
      logger.info("Delete by id failed, it does not exist in the database: " + id);
      throw new IllegalStateException("user with id " + id + " does not exist");
    } else {
      userRepository.deleteById(id);
    }
  }

  @Transactional
  public void updateUser(Long userId, String firstName, String lastName, String email) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalStateException(
            "user with id " + userId + " does not exist"
        ));
    if (firstName != null && firstName.length() > 0 && !Objects
        .equals(user.getFirstName(), firstName)) {
      user.setFirstName(firstName);
    }
    if (lastName != null && lastName.length() > 0 && !Objects
        .equals(user.getLastName(), lastName)) {
      user.setLastName(lastName);
    }
    if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
      Optional<User> userOptional = userRepository.findUserByEmail(email);
      if (userOptional.isPresent()) {
        throw new IllegalStateException("email taken");
      }
      user.setEmail(email);
    }
  }

}
