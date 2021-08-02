package io.catalyte.training.sportsproducts.user;


import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
      throw new ResourceNotFound("Get by id failed. id " + id + " does not exist in the database: ");
    }
  }
  /**
   * Adds a new user with unique email to the database.
   *
   * @param user - the user
   * @return - the user
   */
  public User addUserByEmail(User user) {
    Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
    if (userOptional.isPresent()) {
      logger.info("Add new user failed, email already exists in the database: " + user.getEmail());
      throw new IllegalStateException("Email already exists: " + user.getEmail() );
    }
    userRepository.save(user);
    System.out.println(user);
    return user;
  }



}
