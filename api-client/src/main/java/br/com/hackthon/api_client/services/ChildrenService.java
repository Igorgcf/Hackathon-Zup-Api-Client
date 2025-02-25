package br.com.hackthon.api_client.services;

import br.com.hackthon.api_client.dto.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface ChildrenService {

    Page<ChildrenDTO> findAllPaged(Pageable pageable);

    Page<ChildrenDTO> findAllPagedByFirstName(String firstName, Pageable pageable);

    Page<ChildrenDTO> findAllPagedByLastName(String lastName, Pageable pageable);

    Page<ChildrenDTO> findAllPagedByAge(int age, Pageable pageable);

    Page<ChildrenDTO> findAllPagedByCpf(String cpf, Pageable pageable);

    ChildrenDTO findById(UUID id);

    ChildrenDTO insert(ChildrenDTO dto);

    ChildrenDTO update(UUID id, ChildrenDTO dto);

    void deleteById(UUID id);
}
