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
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyRepository repository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ChildrenRepository childrenRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<FamilyDTO> findAllPaged(Pageable pageable) {

        Page<Family> page = repository.findAll(pageable);
        return page.map(x -> new FamilyDTO(x, x.getMembers(), x.getChildren()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FamilyDTO> findAllPagedByLastName(String lastName, Pageable pageable) {

        Page<Family> page = repository.findAllPagedByLastNameContainingIgnoreCase(lastName, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("Last name not found: " + lastName);
        }
        return page.map(x -> new FamilyDTO(x, x.getMembers(), x.getChildren()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FamilyDTO> findByState(String state, Pageable pageable) {

        Page<Family> page = repository.findAllPagedByStateContainingIgnoreCase(state, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("State not found: " + state);
        }
        return page.map(x -> new FamilyDTO(x, x.getMembers(), x.getChildren()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FamilyDTO> findByCity(String city, Pageable pageable) {

        Page<Family> page = repository.findAllPagedByCityContainingIgnoreCase(city, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("City not found: " + city);
        }
        return page.map(x -> new FamilyDTO(x, x.getMembers(), x.getChildren()));
    }

    @Transactional(readOnly = true)
    @Override
    public FamilyDTO findById(UUID id) {

        Optional<Family> obj = repository.findById(id);
        Family entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        return new FamilyDTO(entity, entity.getMembers(), entity.getChildren());
    }

    @Transactional
    @Override
    public FamilyDTO insert(FamilyDTO dto) {

        Family entity = new Family();
        copyDtoToEntity(entity, dto);
        entity.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);
        return new FamilyDTO(entity, entity.getMembers(), entity.getChildren());
    }

    @Transactional
    @Override
    public FamilyDTO update(UUID id, FamilyDTO dto) {

        Optional<Family> obj = repository.findById(id);
        Family entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        copyDtoToEntity(entity, dto);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);
        return new FamilyDTO(entity, entity.getMembers(), entity.getChildren());
    }

    @Override
    public void deleteById(UUID id) {

        Optional<Family> obj = repository.findById(id);
        if (obj.isEmpty()) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
        repository.deleteById(id);
    }

    void copyDtoToEntity(Family entity, FamilyDTO dto) {

        entity.setLastName(dto.getLastName());
        entity.setState(dto.getState());
        entity.setCity(dto.getCity());
        entity.setStreet(dto.getStreet());
        entity.setAddressNumber(dto.getAddressNumber());

        entity.getMembers().clear();

        if (dto.getMembers() != null && !dto.getMembers().isEmpty()) {
            for (PersonDTO personDTO : dto.getMembers()) {
                if (personDTO.getId() != null) {
                    Optional<Person> obj = personRepository.findById(personDTO.getId());
                    Person person = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + personDTO.getId()));

                    person.setFamily(entity);
                    entity.getMembers().add(person);

                } else {
                    Person newPerson = new Person();

                    newPerson.setFirstName(personDTO.getFirstName());
                    newPerson.setLastName(personDTO.getLastName());
                    newPerson.setDateBirth(personDTO.getDateBirth());
                    newPerson.setAge(personDTO.getAge());
                    newPerson.setCpf(personDTO.getCpf());
                    newPerson.setIncome(personDTO.getIncome());
                    newPerson.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
                    newPerson.setFamily(entity);
                    entity.getMembers().add(newPerson);

                }
            }
        }

        entity.getChildren().clear();

        if (dto.getChildren() != null && !dto.getChildren().isEmpty()) {
            for (ChildrenDTO childrenDTO : dto.getChildren()) {
                if (childrenDTO.getId() != null) {
                    Optional<Children> optional = childrenRepository.findById(childrenDTO.getId());
                    Children children = optional.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + childrenDTO.getId()));
                    children.setFamily(entity);
                    entity.getChildren().add(children);

                } else {

                    Children newChildren = new Children();

                    newChildren.setFirstName(childrenDTO.getFirstName());
                    newChildren.setLastName(childrenDTO.getLastName());
                    newChildren.setDateBirth(childrenDTO.getDateBirth());
                    newChildren.setAge(childrenDTO.getAge());
                    newChildren.setCpf(childrenDTO.getCpf());
                    newChildren.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
                    newChildren.setFamily(entity);
                    entity.getChildren().add(newChildren);

                }
            }
        }
    }
}



