package io.catalyte.training.sportsproducts.domains.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.catalyte.training.sportsproducts.domains.purchase.Purchase;
import io.catalyte.training.sportsproducts.domains.review.Review;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

  @NotBlank(message = "First Name may not be blank, empty or null")
  @Pattern(regexp = "^([A-Za-z])[A-Za-z '-]*$", message = "Invalid Input. Name may only allow letters, apostrophes, spaces, hyphens (-)")
  private String firstName;

  @NotBlank(message = "Last Name may not be blank, empty or null")
  @Pattern(regexp = "^([A-Za-z])[A-Za-z '-]*$", message = "Invalid Input. Name may only allow letters, apostrophes, spaces, hyphens (-)")
  private String lastName;

  @NotBlank(message = "Email is required")
  @Email
  private String email;

  @NotBlank(message = "Street address is required")
  private String streetAddress;

  private String streetAddress2;

  @NotBlank(message = "City is required")
  @Pattern(regexp = "^([A-Za-z])[A-Za-z '-]*$", message = "Invalid Input. City may only allow letters, apostrophes, spaces, hyphens (-)")
  private String city;

  @State
  private String state;

  @NotBlank(message = "Zip code may not be blank")
  @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Invalid Input.")
  private String zipCode;

  @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Invalid Input.")
  private String phoneNumber;

  private String role;

  @OneToMany(mappedBy = "userId")
  private List<Purchase> purchases;

  @OneToMany(mappedBy = "userId")
  private List<Review> reviews;

  public User() {
  }

  public User(String firstName, String lastName, String email,
      String streetAddress, String streetAddress2, String city, String state,
      String zipCode, String phoneNumber, String role, List<Purchase> purchases, List<Review> reviews) {
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
    this.purchases = purchases;
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

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", streetAddress='" + streetAddress + '\'' +
        ", StreetAddress2='" + streetAddress2 + '\'' +
        ", city='" + city + '\'' +
        ", street='" + state + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}



