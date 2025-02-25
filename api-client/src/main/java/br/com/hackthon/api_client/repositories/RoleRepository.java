package br.com.hackthon.api_client.repositories;

import br.com.hackthon.api_client.entities.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Page<Role> findAllByAuthorityContainingIgnoreCase(@Param("authority") String authority, Pageable pageable);

    boolean existsByAuthority(String authority);
}
