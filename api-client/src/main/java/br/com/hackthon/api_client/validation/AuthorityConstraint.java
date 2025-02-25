package br.com.hackthon.api_client.validation;

import jakarta.validation.*;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AuthorityConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityConstraint {

    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  {};
}