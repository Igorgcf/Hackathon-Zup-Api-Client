package br.com.hackthon.api_client.repositories;

import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.services.exceptions.*;
import br.com.hackthon.api_client.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.domain.*;

import java.util.*;

@DataJpaTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private FamilyRepository familyRepository;

    @Test
    public void findAllShouldReturnAllPersonPaged(){

        PageRequest pageable = PageRequest.of(0,12);

        Page<Person> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByFirstNameContainingIgnoreCaseShouldReturnPageWhenFirstNameExists(){

        String firstName = "Ana";

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Person> page = repository.findAllPagedByFirstNameContainingIgnoreCase(firstName, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertEquals(page.getContent().get(0).getFirstName(), firstName);
    }

    @Test
    public void findAllPagedByFirstNameContainingIgnoreCaseShouldThrowResourceNotFoundExceptionWhenFirstNameNonExisting(){

        String firstName = "Bruno";

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<Person> page = repository.findAllPagedByFirstNameContainingIgnoreCase(firstName, pageable);

            if(page.isEmpty()) {
                throw new ResourceNotFoundException("First name not found: " + firstName);
            }
        });
    }

    @Test
    public void findAllPagedByLastNameContainingIgnoreCaseShouldReturnPageWhenLastNameExists(){

        String lastName = "Albuquerque";

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Person> page = repository.findAllPagedByLastNameContainingIgnoreCase(lastName, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByLastNameContainingIgnoreCaseShouldThrowResourceNotFoundExceptionWhenLastNameNonExisting(){

        String lastName = "Maria";

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<Person> page = repository.findAllPagedByLastNameContainingIgnoreCase(lastName, pageable);
            if(page.isEmpty()){
            throw new ResourceNotFoundException("Last name not found: " + lastName);
            }
        });
    }

    @Test
    public void findAllPagedByAgeContainingIgnoreCaseShouldReturnPageWhenAgeExisting(){

        int age = 29;

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Person> page = repository.findAllPagedByAge(age, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByAgeContainingIgnoreCaseShouldThrowResourceNotFoundExceptionWhenAgeNonExisting(){

        int age = 18;

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<Person> page = repository.findAllPagedByAge(age, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("Age not found: " + age);
            }
        });
    }

    @Test
    public void findAllPagedByIncomeShouldReturnPageWhenIncomeExisting(){

        double income = 7000.00;

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Person> page = repository.findAllPagedByIncome(income, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByIncomeShouldThrowResourceNotFoundExceptionWhenIncomeNonExisting(){

        double income = 1700.00;

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<Person> page = repository.findAllPagedByIncome(income, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("Income not found: " + income);
            }
        });
    }

    @Test
    public void findAllPagedByCpfShouldReturnPageWhenCpfExists(){

        String cpf = "323.244.300-10";

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Person> page = repository.findAllPagedByCpfContainingIgnoreCase(cpf, pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findAllPagedByCpfShouldThrowResourceNotFoundExceptionWhenCpfNonExisting(){

        String cpf = "323.244.300-20";

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<Person> page = repository.findAllPagedByCpfContainingIgnoreCase(cpf, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("CPF not found: " + cpf);
            }
        });

    }

    @Test
    public void findByIdShouldReturnPersonByIdWhenIdExists(){

        Optional<Person> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Person> optional = repository.findById(id);

        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id  = UUID.fromString("78ce830a-c0c8-43b0-83cf-ca061fd7a60d");

        Assertions.assertThrows(ResourceNotFoundException.class,() -> {

            Optional<Person> obj = repository.findById(id);
            if(obj.isEmpty()) {
                throw new ResourceNotFoundException("Id not found: " + id);
            }
        });
    }

        @Test
        public void saveShouldInsertPersonWhenCorrectStructure(){

            Person entity = Factory.createdPerson();

            entity = repository.save(entity);

            Assertions.assertNotNull(entity);
            Assertions.assertEquals(6, repository.count());
        }

        @Test
        public void deleteByIdShouldDeletePersonByIdWhenIdExists(){

            Optional<Person> obj = repository.findAll().stream().findFirst();

            UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

            repository.deleteById(id);

            Assertions.assertEquals(4, repository.count());
        }

        @Test
        public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

            UUID id = UUID.fromString("123a-456b-789c-987d-654e");

            Assertions.assertThrows(ResourceNotFoundException.class, () -> {
                repository.deleteById(id);
                throw new ResourceNotFoundException("Id not found: " + id);
            });
        }
}
