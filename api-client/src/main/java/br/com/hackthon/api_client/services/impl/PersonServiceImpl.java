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

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private FamilyRepository familyRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<PersonDTO> findAllPaged(Pageable pageable) {

        Page<Person> page = repository.findAll(pageable);
        return page.map(x -> new PersonDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PersonDTO> findAllPagedByFirstName(String firstName, Pageable pageable) {

        Page<Person> page = repository.findAllPagedByFirstNameContainingIgnoreCase(firstName, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("FirstName not found: " + firstName);
        }
        return page.map(x -> new PersonDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PersonDTO> findAllPagedByLastName(String lastName, Pageable pageable) {

        Page<Person> page = repository.findAllPagedByLastNameContainingIgnoreCase(lastName, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("LastName not found: " + lastName);
        }
        return page.map(x -> new PersonDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PersonDTO> findAllPagedByAge(int age, Pageable pageable) {

        Page<Person> page = repository.findAllPagedByAge(age, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("Age not found: " + age);
        }
        return page.map(x -> new PersonDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PersonDTO> findAllPagedByIncome(double income, Pageable pageable) {

        Page<Person> page = repository.findAllPagedByIncome(income, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("income not found: " + income);
        }
        return page.map(x -> new PersonDTO(x, x.getFamily()));
    }

    @Override
    public Page<PersonDTO> findAllPagedByCpf(String cpf, Pageable pageable) {

        Page<Person> page = repository.findAllPagedByCpfContainingIgnoreCase(cpf, pageable);
        if(page.isEmpty()){
            throw new ResourceNotFoundException("cpf not found: " + cpf);
        }
        return page.map(x -> new PersonDTO(x, x.getFamily()));
    }

    @Transactional(readOnly = true)
    @Override
    public PersonDTO findById(UUID id) {

        Optional<Person> obj = repository.findById(id);
        Person entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        return new PersonDTO(entity, entity.getFamily());
    }

    @Transactional
    @Override
    public PersonDTO insert(PersonDTO dto) {

        Person entity = new Person();
        copyDtoToEntity(entity, dto);
        entity.setCollectionDate((LocalDateTime.now(ZoneId.of("UTC"))));
        repository.save(entity);

        return new PersonDTO(entity, entity.getFamily());
    }

    @Transactional
    @Override
    public PersonDTO update(UUID id, PersonDTO dto) {

        Optional<Person> obj = repository.findById(id);
        Person entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        copyDtoToEntity(entity, dto);
        entity.setCollectionDate((LocalDateTime.now(ZoneId.of("UTC"))));
        repository.save(entity);

        return new PersonDTO(entity, entity.getFamily());
    }

    @Override
    public void deleteById(UUID id) {

        Optional<Person> obj = repository.findById(id);

        if(obj.isEmpty()){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        repository.deleteById(id);

    }

    void copyDtoToEntity(Person entity, PersonDTO dto) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateBirth(dto.getDateBirth());
        entity.setAge(dto.getAge());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());

        if (dto.getFamily() != null) {

            if(dto.getFamily().getId() != null){

                Optional<Family> obj = familyRepository.findById(dto.getFamily().getId());
                Family family = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + dto.getFamily().getId()));
                entity.setFamily(family);
                family.getMembers().add(entity);
            } else {
                Family newFamily = new Family();

                newFamily.setLastName(dto.getFamily().getLastName());
                newFamily.setState(dto.getFamily().getState());
                newFamily.setCity(dto.getFamily().getCity());
                newFamily.setStreet(dto.getFamily().getStreet());
                newFamily.setAddressNumber(dto.getFamily().getAddressNumber());
                newFamily.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));

                entity.setFamily(newFamily);
                newFamily.getMembers().add(entity);
            }
        }
    }
}


