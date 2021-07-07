package io.catalyte.training.sportsproducts.domains.user;

import io.catalyte.training.sportsproducts.domains.auth.*;
import org.springframework.http.*;

public interface UserService {

  public User updateUser(String authorization, Long id, User user);

  public JwtResponse loginUser(User user);

}
