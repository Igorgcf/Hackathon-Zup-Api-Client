package br.com.hackthon.api_client.services;

import br.com.hackthon.api_client.dto.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface PersonService {

    Page<PersonDTO> findAllPaged(Pageable pageable);

    Page<PersonDTO> findAllPagedByFirstName(String firstName, Pageable pageable);

    Page<PersonDTO> findAllPagedByLastName(String lastName, Pageable pageable);

    Page<PersonDTO> findAllPagedByAge(int age, Pageable pageable);

    Page<PersonDTO> findAllPagedByIncome(double income, Pageable pageable);

    Page<PersonDTO> findAllPagedByCpf(String cpf, Pageable pageable);

    PersonDTO findById(UUID id);

    PersonDTO insert(PersonDTO dto);

    PersonDTO update(UUID id, PersonDTO dto);

    void deleteById(UUID id);
}
