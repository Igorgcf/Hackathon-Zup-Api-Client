package br.com.hackthon.api_client.repositories;

import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.services.exceptions.*;
import br.com.hackthon.api_client.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.dao.*;
import org.springframework.data.domain.*;

import java.util.*;

@DataJpaTest
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repository;

    String existingAuthority;
    String nonExistingAuthority;

    @BeforeEach
    void setUp() throws Exception{

        existingAuthority = "Boss";
        nonExistingAuthority = "Assistent";
    }

    @Test
    public void findAllPagedShouldReturnAllRolePaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Role> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByAuthorityShouldReturnAllRolePagedWhenAuthorityExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Role> page = repository.findAllByAuthorityContainingIgnoreCase(existingAuthority, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByAuthorityShouldThrowResourceNotFoundExceptionWhenAuthorityNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<Role> page = repository.findAllByAuthorityContainingIgnoreCase(nonExistingAuthority, pageable);
            throw new ResourceNotFoundException("Authority not found: " + nonExistingAuthority);
        });
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExists(){

        Optional<Role> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Role> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Optional<Role> obj = repository.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

        Role entity = Factory.createdRole();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(6, repository.count());
    }

    @Test
    public void deleteByIdShouldThrowDataIntegrityViolationExceptionWhenIdExists(){

        Optional<Role> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.deleteById(id);
            throw new DataIntegrityViolationException("Integrity Violation Exception");
        });
    }
}
