package io.catalyte.training.sportsproducts.domains.user;

import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateConstraintValidator implements ConstraintValidator<State, String> {
  private final List<String> state = Arrays.asList("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE",
                                                   "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS",
                                                    "KY", "LA", "ME", "MD", "MA", "MH", "MI", "MN","MS", "MO", "MP","MT",
                                                    "NE", "NV", "NH", "NJ", "NM","NY", "NC", "ND", "MP", "OH", "OK",
                                                    "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
                                                    "VA", "VI", "WA", "WV", "WI", "WY", "FM");



  @Override
  public void initialize(State constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    return state.contains(value);
  }

}
