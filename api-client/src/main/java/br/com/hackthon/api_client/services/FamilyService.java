package br.com.hackthon.api_client.services;

import br.com.hackthon.api_client.dto.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface FamilyService {

    Page<FamilyDTO> findAllPaged(Pageable pageable);

    Page<FamilyDTO> findAllPagedByLastName(String lastName, Pageable pageable);

    Page<FamilyDTO> findByState(String state, Pageable pageable);

    Page<FamilyDTO> findByCity(String city, Pageable pageable);

    FamilyDTO findById(UUID id);

    FamilyDTO insert(FamilyDTO dto);

    FamilyDTO update(UUID id, FamilyDTO dto);

    void deleteById(UUID id);
}
