package io.catalyte.training.sportsproducts.user;

import java.util.List;

public interface UserService {

  List<User> getUsers(User user);

  User getUserById(Long id);




}
