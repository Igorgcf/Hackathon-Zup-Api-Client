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

@Transactional
@Service
public class ChildrenServiceImpl implements ChildrenService {

    @Autowired
    private ChildrenRepository repository;

    @Autowired
    private FamilyRepository familyRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<ChildrenDTO> findAllPaged(Pageable pageable) {

        Page<Children> page = repository.findAll(pageable);

        return page.map(x -> new ChildrenDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ChildrenDTO> findAllPagedByFirstName(String firstName, Pageable pageable) {

        Page<Children> page = repository.findAllPagedByFirstNameContainingIgnoreCase(firstName, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("First name not found: " + firstName);
        }
        return page.map(x -> new ChildrenDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ChildrenDTO> findAllPagedByLastName(String lastName, Pageable pageable) {

        Page<Children> page = repository.findAllPagedByLastNameContainingIgnoreCase(lastName, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("Last name not found: " + lastName);
        }
        return page.map(x -> new ChildrenDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ChildrenDTO> findAllPagedByAge(int age, Pageable pageable) {

        Page<Children> page = repository.findAllPagedByAge(age, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("Age not found: " + age);
        }
        return page.map(x -> new ChildrenDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ChildrenDTO> findAllPagedByCpf(String cpf, Pageable pageable) {

        Page<Children> page = repository.findAllPagedByCpfContainingIgnoreCase(cpf, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("CPF not found: " + cpf);
        }
        return page.map(x -> new ChildrenDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public ChildrenDTO findById(UUID id) {

        Optional<Children> obj = repository.findById(id);
        Children entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        return new ChildrenDTO(entity, entity.getFamily());
    }

    @Override
    public ChildrenDTO insert(ChildrenDTO dto) {

        Children entity = new Children();
        copyDtoToEntity(entity, dto);
        repository.save(entity);
        return new ChildrenDTO(entity, entity.getFamily());
    }

    @Override
    public ChildrenDTO update(UUID id, ChildrenDTO dto) {

        Optional<Children> obj = repository.findById(id);
        Children entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        copyDtoToEntity(entity, dto);
        repository.save(entity);
        return new ChildrenDTO(entity, entity.getFamily());
    }

    @Override
    public void deleteById(UUID id) {

        Optional<Children> obj = repository.findById(id);

        if(obj.isEmpty()){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        repository.deleteById(id);
    }

    void copyDtoToEntity(Children entity, ChildrenDTO dto){

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateBirth(dto.getDateBirth());
        entity.setAge(dto.getAge());
        entity.setCpf(dto.getCpf());
        entity.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));

        if (dto.getFamily() != null) {

            if(dto.getFamily().getId() != null){

                Family family = familyRepository.findById(dto.getFamily().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Id not found: " + dto.getFamily().getId()));
                entity.setFamily(family);
                family.getChildren().add(entity);
            } else {
                Family newFamily = new Family();
                newFamily.setLastName(dto.getFamily().getLastName());
                newFamily.setState(dto.getFamily().getState());
                newFamily.setCity(dto.getFamily().getCity());
                newFamily.setStreet(dto.getFamily().getStreet());
                newFamily.setAddressNumber(dto.getFamily().getAddressNumber());
                newFamily.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
                newFamily.getChildren().add(entity);
                entity.setFamily(newFamily);

            }
        }
    }
}
