package br.com.hackthon.api_client.services.impl;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.repositories.*;
import br.com.hackthon.api_client.services.*;
import br.com.hackthon.api_client.services.exceptions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<RoleDTO> findAllPaged(Pageable pageable) {

        Page<Role> page = repository.findAll(pageable);
        return page.map(RoleDTO::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<RoleDTO> findAllPagedByAuthority(String authority, Pageable pageable) {

        Page<Role> page = repository.findAllByAuthorityContainingIgnoreCase(authority, pageable);
        if (page.isEmpty()) {
            throw new ResourceNotFoundException("Authority not found: " + authority);
        }
        return page.map(RoleDTO::new);
    }

    @Transactional(readOnly = true)
    @Override
    public RoleDTO findById(UUID id) {

        Optional<Role> obj = repository.findById(id);
        Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        return new RoleDTO(entity);
    }

    @Override
    public RoleDTO insert(RoleDTO dto) {

        Role entity = new Role();

        entity.setAuthority(dto.getAuthority());
        repository.save(entity);
        return new RoleDTO(entity);
    }

    @Override
    public RoleDTO update(UUID id, RoleDTO dto) {

        Optional<Role> obj = repository.findById(id);
        Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        entity.setAuthority(dto.getAuthority());
        repository.save(entity);

        return new RoleDTO(entity);
    }

    @Override
    public void deleteById(UUID id) {

        try {
        Optional<Role> obj = repository.findById(id);
        if (obj.isEmpty()) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }

            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Integrity violation Exception");
        }
    }
}
