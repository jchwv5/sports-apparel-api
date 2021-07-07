package io.catalyte.training.sportsproducts.domains.user;

import io.catalyte.training.sportsproducts.domains.auth.*;
import io.catalyte.training.sportsproducts.exceptions.*;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.http.*;
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
  public User updateUser(String authorization, Long id, User user) {

    // GET TOKEN FROM HEADERS
    String token = jwtTokenUtil.getTokenFromAuthorization(authorization);

    String email;

    try {
      email = jwtTokenUtil.getEmailFromToken(token);
    } catch (ExpiredJwtException e) {
      throw new RuntimeException(e.getMessage());
    }

    if (!email.equals(user.getEmail())) {
      throw new RuntimeException("JWT Token is bad");
    }

    User existingUser;

    try {
      existingUser = userRepository.findById(id).orElse(null);
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

    String token = jwtTokenUtil.generateJwtToken(existingUser);

    return new JwtResponse(token, existingUser);

  }

}
