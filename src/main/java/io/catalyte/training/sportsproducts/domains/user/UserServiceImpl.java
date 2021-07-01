package io.catalyte.training.sportsproducts.domains.user;

import io.catalyte.training.sportsproducts.domains.auth.*;
import io.catalyte.training.sportsproducts.exceptions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private final UserRepository userRepository;

  JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

  public UserServiceImpl(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User createUser(User user) {
    try {
      return userRepository.save(user);
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }
  }

  @Override
  public User updateUser(User user) {
    User existingUser;
    try {
      existingUser = userRepository.findById(user.getId()).orElse(null);
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }

    if (existingUser == null) {
      throw new ResourceNotFound("User with id: " + user.getId() + " does not exist");
    }

    try {
      return userRepository.save(user);
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }

  }

  @Override
  public JwtResponse loginUser(User user) {
    User existingUser;
    try {
      existingUser = userRepository.findByEmail(user.getEmail());
    } catch (DataAccessException dae) {
      throw new ServerError(dae.getMessage());
    }

    if (existingUser == null) {
      existingUser = userRepository.save(user);
    }

    String token = jwtTokenUtil.generateToken(existingUser);

    return new JwtResponse(token, existingUser);

  }

}
