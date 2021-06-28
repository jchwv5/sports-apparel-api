package io.catalyte.training.sportsproducts.domains.user;

import javax.persistence.*;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String email;
  String role;

  public User() {}

  public User(Long id, String email, String role) {
    this.id = id;
    this.email = email;
    this.role = role;
  }

  public User(String email, String role) {
    this.email = email;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", role='" + role + '\'' +
        '}';
  }
}
