package io.catalyte.training.sportsproducts.domains.user;

/**
 * This interface provides an abstraction layer for the User Service
 */
public interface UserService {

  User updateUser(String credentials, Long id, User user);

  User loginUser(String credentials, User user);

  User createUser(User user);

  User getUserById(Long id);

  User createUserWithoutValidation(User user);


}
