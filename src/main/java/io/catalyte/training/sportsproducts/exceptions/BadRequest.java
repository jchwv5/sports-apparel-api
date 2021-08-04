package io.catalyte.training.sportsproducts.exceptions;

/**
 * A custom exception for a bad request.
 */
public class BadRequest extends RuntimeException {

  public BadRequest() {
  }

  public BadRequest(String message) {
    super(message);
  }
}