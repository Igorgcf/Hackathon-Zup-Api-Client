package br.com.hackthon.api_client.services;

import br.com.hackthon.api_client.dto.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface RoleService {

    Page<RoleDTO> findAllPaged(Pageable pageable);

    Page<RoleDTO> findAllPagedByAuthority(String authority, Pageable pageable);

    RoleDTO findById(UUID id);

    RoleDTO insert(RoleDTO dto);

    RoleDTO update(UUID id, RoleDTO dto);

    void deleteById(UUID id);
}
