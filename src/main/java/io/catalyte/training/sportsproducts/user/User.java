package io.catalyte.training.sportsproducts.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class is a representation of a user.
 */
@Entity
@Table(name = "User", schema = "public")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String streetAddress;
  private String getStreetAddress2;
  private String city;
  private String state;
  private String zipCode;
  private String phoneNumber;

  public User() {
  }

  public User(Long id, String firstName, String lastName, String email,
      String streetAddress, String getStreetAddress2, String city, String street,
      String zipCode, String phoneNumber) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.streetAddress = streetAddress;
    this.getStreetAddress2 = getStreetAddress2;
    this.city = city;
    this.state = street;
    this.zipCode = zipCode;
    this.phoneNumber = phoneNumber;
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

  public String getGetStreetAddress2() {
    return getStreetAddress2;
  }

  public void setGetStreetAddress2(String getStreetAddress2) {
    this.getStreetAddress2 = getStreetAddress2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return state;
  }

  public void setStreet(String street) {
    this.state = street;
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



  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", streetAddress='" + streetAddress + '\'' +
        ", getStreetAddress2='" + getStreetAddress2 + '\'' +
        ", city='" + city + '\'' +
        ", street='" + state + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}



