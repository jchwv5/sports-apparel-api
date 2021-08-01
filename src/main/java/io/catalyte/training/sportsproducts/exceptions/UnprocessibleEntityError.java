package io.catalyte.training.sportsproducts.exceptions;

public class UnprocessibleEntityError extends RuntimeException {

  public UnprocessibleEntityError() {
  }

  public UnprocessibleEntityError(String message) {
    super(message);
  }
}
