package io.catalyte.training.sportsproducts.domains.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.catalyte.training.sportsproducts.domains.review.Review;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class is a representation of a user.
 */
@Entity
@Table(name = "User", schema = "public")
@JsonInclude(Include.NON_NULL)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;

  private String streetAddress;
  private String streetAddress2;
  private String city;
  private String state;
  private String zipCode;
  private String phoneNumber;
  private String role;

  private String email;

  @Column(name = "last_active_time", columnDefinition="timestamp with time zone")
  private LocalDateTime lastActiveTime;

  @OneToMany(mappedBy = "userId")
  private List<Review> reviews;

  public User() {
  }

  public User(String firstName, String lastName, String email, String streetAddress,
      String streetAddress2, String city, String state, String zipCode, String phoneNumber,
      String role, LocalDateTime lastActiveTime,
      List<Review> reviews) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.streetAddress = streetAddress;
    this.streetAddress2 = streetAddress2;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.lastActiveTime = lastActiveTime;
    this.reviews = reviews;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getStreetAddress2() {
    return streetAddress2;
  }

  public void setStreetAddress2(String StreetAddress2) {
    this.streetAddress2 = StreetAddress2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public LocalDateTime getLastActiveTime() {
    return lastActiveTime;
  }

  public void setLastActiveTime(LocalDateTime lastActiveTime) {
    this.lastActiveTime = lastActiveTime;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", streetAddress='" + streetAddress + '\'' +
        ", streetAddress2='" + streetAddress2 + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", role='" + role + '\'' +
        ", lastActiveTime=" + lastActiveTime +
        ", reviews=" + reviews +
        '}';
  }
}



