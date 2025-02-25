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
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    String ExistsUserName;
    String nonExistingUserName;
    String existsEmail;
    String nonExistingEmail;
    String existsCpf;
    String nonExistingCpf;

    @BeforeEach
    void setUp() throws Exception {

        ExistsUserName = "HenriqueTechnology";
        nonExistingUserName = "PauloTechnology";
        existsEmail = "henrique@gmail.com";
        nonExistingEmail = "Paulo@gmail.com";
        existsCpf = "231.123.312-0";
        nonExistingCpf = "231.321.213-0";
    }

    @Test
    public void findAllPagedShouldReturnAllUsersPaged() {

        PageRequest pageable = PageRequest.of(0, 12);

        Page<User> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnUserByIdWhenIdExists() {

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<User> optional = repository.findById(id);


        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() {

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Optional<User> optional = repository.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void findAllByUserNameContainingIgnoreCaseShouldReturnAllUsersByUserNameWhenUserNameExists() {

        List<User> list = repository.findByUsernameContainingIgnoreCase(ExistsUserName);

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }


    @Test
    public void findAllByUserNameShouldThrowResourceNotFoundExceptionWhenUserNameNonExisting() {


        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<User> list = repository.findByUsernameContainingIgnoreCase(nonExistingUserName);
            throw new ResourceNotFoundException("UserName non Existing " + nonExistingUserName);
        });
    }

    @Test
    public void findAllByEmailShouldReturnAllUsersByEmailWhenEmailExists() {

        List<User> list = repository.findByEmailContainingIgnoreCase(existsEmail);

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void findAllByEmailShouldThrowResourceNotFoundExceptionWhenEmailNonExisting() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<User> list = repository.findByEmailContainingIgnoreCase(nonExistingEmail);
            throw new ResourceNotFoundException("E-mail not found: " + nonExistingEmail);
        });
    }

    @Test
    public void findAllByCpfShouldReturnAllUsersByCpfWhenCpfExists() {

        List<User> list = repository.findByCpfContainingIgnoreCase(existsCpf);

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void findAllByCpfShouldThrowResourceNotFoundExceptionWhenCpfNonExisting() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<User> list = repository.findByCpfContainingIgnoreCase(nonExistingCpf);
            throw new ResourceNotFoundException("CPF not found: " + nonExistingCpf);
        });
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure() {

        User entity = Factory.createdUser();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(6, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteUserByIdWhenIdExists() {

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        repository.deleteById(id);

        Assertions.assertEquals(4 , repository.count());
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

