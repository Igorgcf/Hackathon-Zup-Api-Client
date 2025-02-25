package br.com.hackthon.api_client.validation;

import br.com.hackthon.api_client.repositories.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        boolean existsByUsername = userRepository.existsByUsername(username);
        return !existsByUsername;
    }
}
