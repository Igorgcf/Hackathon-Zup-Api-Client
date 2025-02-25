package br.com.hackthon.api_client.validation;

import br.com.hackthon.api_client.repositories.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;

public class EmailConstraintImpl implements ConstraintValidator<EmailConstraint, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        boolean existsByEmail = userRepository.existsByEmail(email);
        return !existsByEmail;
    }
}
