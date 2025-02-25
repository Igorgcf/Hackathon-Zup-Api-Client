package br.com.hackthon.api_client.repositories;

import br.com.hackthon.api_client.entities.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface FamilyRepository extends JpaRepository<Family, UUID> {

    Page<Family> findAllPagedByLastNameContainingIgnoreCase(@Param("lastName") String lastName, Pageable pageable);

    Page<Family> findAllPagedByStateContainingIgnoreCase(@Param("state") String state, Pageable pageable);

    Page<Family> findAllPagedByCityContainingIgnoreCase(@Param("city") String city, Pageable pageable);

}
