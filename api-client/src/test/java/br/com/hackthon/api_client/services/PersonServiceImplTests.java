package br.com.hackthon.api_client.services;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.repositories.*;
import br.com.hackthon.api_client.services.exceptions.*;
import br.com.hackthon.api_client.services.impl.*;
import br.com.hackthon.api_client.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;

import java.util.*;

@SpringBootTest
public class PersonServiceImplTests {

    @Autowired
    private PersonServiceImpl service;

    @Autowired
    private PersonRepository repository;


    @Test
    public void findAllPagedShouldReturnAllPersonPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<PersonDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByFirstNameShouldReturnAllPersonPagedByFirstNameWhenFirstNameExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        String firstName = "Ana";

        Page<PersonDTO> page = service.findAllPagedByFirstName(firstName, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByFirstNameShouldThrowResourceNotFoundExceptionWhenFirstNameNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        String firstName = "Paula";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<PersonDTO> page = service.findAllPagedByFirstName(firstName, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("FirstName not found: " + firstName);
            }
        });
    }

    @Test
    public void findAllPagedByLastNameShouldReturnAllPersonPageByLastNamedWhenLastNameExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        String lastName = "Albuquerque";

        Page<PersonDTO> page = service.findAllPagedByLastName(lastName, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByLastNameShouldThrowResourceNotFoundExceptionWhenLastNameNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        String lastName = "Valdez";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<PersonDTO> page = service.findAllPagedByLastName(lastName, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("LastName not found: " + lastName);
            }
        });
    }

    @Test
    public void findAllPagedByAgeShouldReturnAllPersonPagedByAgeWhenAgeExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        int age = 29;

        Page<PersonDTO> page = service.findAllPagedByAge(age, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByAgeShouldThrowResourceNotFoundExceptionWhenAgeNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        int age = 100;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<PersonDTO> page = service.findAllPagedByAge(age, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("Age not found: " + age);
            }
        });
    }

    @Test
    public void findAllPagedByIncomeShouldReturnAllPersonPagedByIncomeWhenIncomeExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        double income = 6000.00;

        Page<PersonDTO> page = service.findAllPagedByIncome(income, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByIncomeShouldThrowResourceNotFoundExceptionWhenIncomeNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        double income = 2000.00;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<PersonDTO> page = service.findAllPagedByIncome(income, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("Income not found: " + income);
            }
        });
    }

    @Test
    public void findAllPagedByCpfShouldReturnAllPersonPagedByCpfWhenCpfExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        String cpf = "323.244.300-10";

        Page<PersonDTO> page = service.findAllPagedByCpf(cpf, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByCpfShouldThrowResourceNotFoundExceptionWhenCpfNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        String cpf = "123.321.456-10";

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<PersonDTO> page = service.findAllPagedByCpf(cpf, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("CPF not found: " + cpf);
            }
        });
    }

    @Test
    public void findByIdShouldReturnPersonByIdWhenIdExists(){

        Optional<Person> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        PersonDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            PersonDTO dto = service.findById(id);

            if(dto == null){
                throw new ResourceNotFoundException("Id not found: " + id);
            }
        });
    }

    @Test
    public void insertShouldSavePersonWhenCorrectStructure(){

        PersonDTO dto = Factory.createdPersonDtoToInsert();

        dto = service.insert(dto);

        Assertions.assertEquals(6, repository.count());

    }

    @Test
    public void updateShouldSavePersonWhenIdExistingAndCorrectStructure(){

        Optional<Person> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        PersonDTO dto = Factory.createdPersonDtoUpdate();

        dto = service.update(id, dto);

        Assertions.assertEquals(id, dto.getId());
        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            PersonDTO dto = Factory.createdPersonDtoUpdate();
            dto = service.update(id, dto);

            if(dto.getId() != id) {
                throw new ResourceNotFoundException("Id not found: " + id);
            }
        });
    }

    @Test
    public void deleteByIdShouldDeletePersonByIdWhenIdExists(){

        Optional<Person> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }
}
