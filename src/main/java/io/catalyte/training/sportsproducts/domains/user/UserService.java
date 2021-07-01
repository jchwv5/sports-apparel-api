package io.catalyte.training.sportsproducts.domains.user;

import io.catalyte.training.sportsproducts.domains.auth.*;

public interface UserService {

  public User createUser(User user);

  public User updateUser(User user);

  public JwtResponse loginUser(User user);

}
