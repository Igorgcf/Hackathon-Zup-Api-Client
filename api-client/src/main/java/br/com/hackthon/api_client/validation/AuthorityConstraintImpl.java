package br.com.hackthon.api_client.validation;

import br.com.hackthon.api_client.repositories.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;

public class AuthorityConstraintImpl implements ConstraintValidator<AuthorityConstraint, String> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void initialize(AuthorityConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String authority, ConstraintValidatorContext context) {

        boolean existsByAuthority = roleRepository.existsByAuthority(authority);
        return !existsByAuthority;
    }
}
