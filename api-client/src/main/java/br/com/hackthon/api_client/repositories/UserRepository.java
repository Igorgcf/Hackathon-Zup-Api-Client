package br.com.hackthon.api_client.repositories;

import br.com.hackthon.api_client.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(@Param("username") String userName);

    List<User> findByEmailContainingIgnoreCase(@Param("email") String email);

    List<User> findByCpfContainingIgnoreCase(@Param("cpf") String cpf);


}
