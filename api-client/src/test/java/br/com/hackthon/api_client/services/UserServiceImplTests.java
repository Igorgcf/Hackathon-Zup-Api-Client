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
public class UserServiceImplTests {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    String existsUserName;
    String nonExistsUserName;
    String existsEmail;
    String nonExistingEmail;
    String existsCpf;
    String nonExistingCpf;

    @BeforeEach
    void setUp() throws Exception {

        existsUserName = "HenriqueTechnology";
        nonExistsUserName = "PauloTechnology";
        existsEmail = "Henrique@gmail.com";
        nonExistingEmail = "Paulo@gmail.com";
        existsCpf = "231.123.312-0";
        nonExistingCpf = "231.123.312-1";

    }

    @Test
    public void findAllPagedShouldReturnAllUsersPaged() {

        PageRequest pageable = PageRequest.of(0, 12);

        Page<UserDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllByUserNameShouldReturnAllUsersBysUserName() {

        List<UserDTO> list = service.findByUsername(existsUserName);

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void findAllByUserNameShouldThrowResourceNotFoundExceptionWhenUserNameNonExisting() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            List<UserDTO> list = service.findByUsername(nonExistsUserName);
            throw new ResourceNotFoundException("Username not found: " + nonExistsUserName);
        });
    }

    @Test
    public void findAllByEmailShouldReturnAllUsersByEmailWhenEmailExists() {

        List<UserDTO> list = service.findByEmail(existsEmail);

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void findAllByEmailShouldThrowResourceNotFoundExceptionWhenEmailNonExisting() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            List<UserDTO> list = service.findByEmail(nonExistingEmail);
            throw new ResourceNotFoundException("E-mail not found: " + nonExistingEmail);
        });
    }

    @Test
    public void findAllByCpfShouldReturnAllUsersByCpfWhenCpfExists() {

        List<UserDTO> list = service.findByCpf(existsCpf);

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void findAllByCpfShouldThrowResourceNotFoundExceptionWhenCpfNonExisting() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            List<UserDTO> list = service.findByCpf(nonExistingCpf);
            throw new ResourceNotFoundException("CPF not found: " + nonExistingCpf);
        });
    }

    @Test
    public void findByIdShouldReturnUserByIdWhenIdExists() {

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            UserDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        UserDTO dto = Factory.createdUserDtoToInsert();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(6, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            UserDTO dto = Factory.createdUserDtoToUpdate();
            dto  = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldDeleteUserByIdWhenIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();

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
