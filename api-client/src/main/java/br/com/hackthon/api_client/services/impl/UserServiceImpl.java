package br.com.hackthon.api_client.services.impl;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.repositories.*;
import br.com.hackthon.api_client.services.*;
import br.com.hackthon.api_client.services.exceptions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<UserDTO> findAllPaged(Pageable pageable) {

        Page<User> page = repository.findAll(pageable);
        return page.map(x -> new UserDTO(x, x.getRoles()));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findByUsername(String userName) {

        List<User> list = repository.findByUsernameContainingIgnoreCase(userName);
        if(list.isEmpty()) {
            throw new ResourceNotFoundException("Username not found: " + userName);
        }
        return list.stream().map(x -> new UserDTO(x, x.getRoles())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findByEmail(String email) {

        List<User> list = repository.findByEmailContainingIgnoreCase(email);
        if(list.isEmpty()){
            throw new ResourceNotFoundException("Email not found: " + email);
        }
        return list.stream().map(x -> new UserDTO(x, x.getRoles())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findByCpf(String cpf) {

        List<User> list = repository.findByCpfContainingIgnoreCase(cpf);
        if(list.isEmpty()){
            throw new ResourceNotFoundException("CPF not found: " + cpf);
        }
        return list.stream().map(x -> new UserDTO(x, x.getRoles())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO findById(UUID id) {

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        return new UserDTO(entity, entity.getRoles());
    }

    @Transactional
    @Override
    public UserDTO insert(UserDTO dto) {

        User entity = new User();
        copyDtoToEntity(entity, dto);
        entity.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);
        return new UserDTO(entity, entity.getRoles());
    }

    @Transactional
    @Override
    public UserDTO update(UUID id, UserDTO dto) {

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        copyDtoToEntity(entity, dto);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        return new UserDTO(entity, entity.getRoles());
    }

    @Override
    public void passwordUpdate(UUID id, UserDTO dto) {

        Optional<User> obj = repository.findById(id);
        if(!obj.isPresent()){
            throw new ResourceNotFoundException(("Id not found: ") + id);
        }if(!obj.get().getPassword().equals(dto.getOldPassword())){
            throw new BadRequestException(("Error: Mismatched old password!"));
        }else{
            User entity = obj.get();
            entity.setPassword(dto.getPassword());
            entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            repository.save(entity);
        }
    }

    @Override
    public void deleteById(UUID id) {

        Optional<User> obj = repository.findById(id);
        if (obj.isEmpty()) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
        repository.deleteById(id);
    }

    void copyDtoToEntity(User entity, UserDTO dto) {

        if(dto.getUsername() != null){
            entity.setUsername(dto.getUsername());
        }

        if(dto.getEmail() != null){
            entity.setEmail(dto.getEmail());
        }

        if(dto.getPassword() != null){
            entity.setPassword(dto.getPassword());
        }

        if(dto.getFullName() != null){
            entity.setFullName(dto.getFullName());
        }

        if(dto.getPhoneNumber() != null){
            entity.setPhoneNumber(dto.getPhoneNumber());
        }

        if(dto.getCpf() != null){
            entity.setCpf(dto.getCpf());
        }

        if(dto.getImageUrl() != null){
            entity.setImageUrl(dto.getImageUrl());
        }

        entity.getRoles().clear();

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            for (RoleDTO roleDTO : dto.getRoles()) {
                if (roleDTO.getId() != null) {
                    Optional<Role> obj = roleRepository.findById(roleDTO.getId());
                    Role role = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + roleDTO.getId()));
                    entity.getRoles().add(role);

                } else {

                    Role newRole = new Role();
                    newRole.setAuthority(roleDTO.getAuthority());
                    roleRepository.save(newRole);

                    entity.getRoles().add(newRole);
                }
            }
        }
    }
}

