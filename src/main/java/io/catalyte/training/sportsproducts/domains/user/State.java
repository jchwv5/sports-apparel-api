package io.catalyte.training.sportsproducts.domains.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = StateConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface State {

  String message() default "State is required and must be two-Letter state abbreviations";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
