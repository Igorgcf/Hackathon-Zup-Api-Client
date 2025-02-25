package br.com.hackthon.api_client.repositories;

import br.com.hackthon.api_client.entities.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface ChildrenRepository extends JpaRepository<Children, UUID> {

    Page<Children> findAllPagedByFirstNameContainingIgnoreCase(@Param("firstName") String firstName, Pageable pageable);

    Page<Children> findAllPagedByLastNameContainingIgnoreCase(@Param("lastName") String lastName, Pageable pageable);

    Page<Children> findAllPagedByAge(@Param("age") int age, Pageable pageable);

    Page<Children> findAllPagedByCpfContainingIgnoreCase(@Param("cpf") String cpf, Pageable pageable);

    boolean existsByCpf(String cpf);
}
