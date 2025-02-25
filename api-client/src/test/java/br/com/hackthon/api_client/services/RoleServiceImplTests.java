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
import org.springframework.dao.*;
import org.springframework.data.domain.*;

import java.util.*;

@SpringBootTest
public class RoleServiceImplTests {

    @Autowired
    private RoleServiceImpl service;

    @Autowired
    private RoleRepository repository;

    String existingAuthority;
    String nonExistingAuthority;

    @BeforeEach
    void setUp() throws Exception{

        existingAuthority = "CEO";
        nonExistingAuthority = "Seller";

    }

    @Test
    public void findAllPagedShouldReturnAllRolesPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<RoleDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByAuthorityShouldReturnAllRolesByAuthorityWhenAuthorityExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<RoleDTO> page = service.findAllPagedByAuthority(existingAuthority, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByAuthorityShouldThrowResourceNotFoundExceptionWhenAuthorityNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<RoleDTO> page = service.findAllPagedByAuthority(nonExistingAuthority, pageable);
            throw new ResourceNotFoundException("Authority not found: " + nonExistingAuthority);
        });
    }

    @Test
    public void findByIdShouldReturnRoleByIdWhenIdExists(){

        Optional<Role> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(id, dto.getId());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            RoleDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        RoleDTO dto = Factory.createdRoleDtoToInsert();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(6, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExists(){

        Optional<Role> obj = repository.findAll().stream().findFirst();;

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = Factory.createdRoleDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            RoleDTO dto = Factory.createdRoleDtoToUpdate();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldThrowDataIntegrityViolationExceptionWhenIdExists(){

        Optional<Role> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            service.deleteById(id);
            throw new DataIntegrityViolationException("Integrity Violation Exception");
        });
    }
}
