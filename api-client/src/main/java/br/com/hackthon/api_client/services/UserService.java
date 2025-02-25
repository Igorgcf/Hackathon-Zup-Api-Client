package br.com.hackthon.api_client.services;

import br.com.hackthon.api_client.dto.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface UserService {

    Page<UserDTO> findAllPaged(Pageable pageable);

    List<UserDTO>  findByUsername(String userName);

    List<UserDTO> findByEmail(String email);

    List<UserDTO> findByCpf(String cpf);

    UserDTO findById(UUID id);

    UserDTO insert(UserDTO dto);

    UserDTO update(UUID id, UserDTO dto);

    void passwordUpdate(UUID id, UserDTO dto);

    void deleteById(UUID id);
}
