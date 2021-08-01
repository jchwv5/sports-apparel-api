package io.catalyte.training.sportsproducts.exceptions;

/**
 * A custom exception for unprocessable service errors.
 */
public class UnprocessableEntityError extends RuntimeException {

  public UnprocessableEntityError() {
  }

  public UnprocessableEntityError(String message) {
    super(message);
  }
}
