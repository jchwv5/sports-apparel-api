package io.catalyte.training.sportsproducts.exceptions;

/**
 * A custom exception for unprocessable entity errors.
 */
public class UnprocessableEntityError extends RuntimeException {
  public UnprocessableEntityError() {
  }

  public UnprocessableEntityError(String message) {
    super(message);
  }
}
