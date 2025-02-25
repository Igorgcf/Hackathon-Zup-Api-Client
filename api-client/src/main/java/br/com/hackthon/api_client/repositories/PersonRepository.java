package br.com.hackthon.api_client.repositories;

import br.com.hackthon.api_client.entities.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    Page<Person> findAllPagedByFirstNameContainingIgnoreCase(@Param("firstName") String firstName, Pageable pageable);

    Page<Person> findAllPagedByLastNameContainingIgnoreCase(@Param("lastName") String lastName, Pageable pageable);

    Page<Person> findAllPagedByAge(@Param("age") int age, Pageable pageable);

    Page<Person> findAllPagedByIncome(@Param("income") double income, Pageable pageable);

    Page<Person> findAllPagedByCpfContainingIgnoreCase(@Param("cpf") String cpf, Pageable pageable);

    boolean existsByCpf(String cpf);
}
