package io.catalyte.training.sportsproducts.domains.user;

import io.catalyte.training.sportsproducts.exceptions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private final UserRepository userRepository;

  public UserServiceImpl(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User authorizeUser(User user) {

    User savedUser;
    // FIND USER
    try {
      savedUser = userRepository.findById(user.getId()).orElse(null);
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }

    // IF USER DOESN'T EXIST, SAVE USER. ELSE, RETURN USER
    if (savedUser == null) {
      try {
        return userRepository.save(user);
      } catch (DataAccessException dae) {
        throw new ServerError(dae.getMessage());
      }
    } else {
      return savedUser;
    }

  }

}
