package br.com.hackthon.api_client.validation;

import br.com.hackthon.api_client.repositories.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;

public class CpfConstraintImpl implements ConstraintValidator<CpfConstraint, String> {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private ChildrenRepository childrenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(CpfConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {

        boolean cpfExistsInPerson = repository.existsByCpf(cpf);
        boolean cpfExistsInChildren = childrenRepository.existsByCpf(cpf);
        boolean cpfExistsInUser = userRepository.existsByCpf(cpf);
        return !(cpfExistsInPerson || cpfExistsInChildren || cpfExistsInUser);
    }
}
