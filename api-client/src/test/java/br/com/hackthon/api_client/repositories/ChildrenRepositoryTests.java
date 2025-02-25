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
public class ChildrenRepositoryTests {

    String firstName;
    String lastName;
    Integer age;
    String cpf;

    @BeforeEach
    void setUp() throws Exception{

        firstName = "Pedro";
        lastName = "Pereira Almeida";
        age = 18;
        cpf = "832.092.230-50";
    }

    @Autowired
    private ChildrenRepository repository;

    @Test
    public void findAllShouldReturnAllChildrenPaged(){

        Pageable pageable = PageRequest.of(0, 12);

        Page<Children> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByShouldReturnChildrenByIdWhenIdExists(){

        Optional<Children> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Children> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Optional<Children> obj = repository.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void findAllByFirstNameShouldReturnAllChildrenPagedByFirstNameWhenFirstNameExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Children> page = repository.findAllPagedByFirstNameContainingIgnoreCase(firstName,pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }
    
    @Test
    public void findAllByLastNameShouldReturnAllChildrenPagedByLastNameWhenLastNameExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Children> page = repository.findAllPagedByLastNameContainingIgnoreCase(lastName, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllByAgeShouldReturnAllChildrenPagedByAgeWhenAgeExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Children> page = repository.findAllPagedByAge(age, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllByCpfShouldReturnAllChildrenPagedByCpfWhenCpfExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Children> page = repository.findAllPagedByCpfContainingIgnoreCase(cpf, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        Children entity = Factory.createdChildren();

        repository.save(entity);

        Assertions.assertEquals(10, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExists(){

        Optional<Children> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        repository.deleteById(id);

        Assertions.assertEquals(8, repository.count());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            repository.deleteById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }
}
