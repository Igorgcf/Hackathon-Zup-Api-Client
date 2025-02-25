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
public class ChildrenServiceImplTests {

    @Autowired
    private ChildrenServiceImpl service;

    @Autowired
    private ChildrenRepository repository;

    String existingFirstName;
    String nonExistingFirstName;
    String existingLastName;
    String nonExistingLastName;
    Integer existingAge;
    Integer nonExistingAge;
    String existingCpf;
    String nonExistingCpf;

    @BeforeEach
    void setUp() throws Exception{

        existingFirstName = "Ricardo";
        nonExistingFirstName = "Maria";
        existingLastName = "Pereira Almeida";
        nonExistingLastName = "Souza Silva";
        existingAge = 19;
        nonExistingAge = 27;
        existingCpf = "742.218.630-56";
        nonExistingCpf = "832.092.230-51";

    }

    @Test
    public void findAllPagedShouldReturnAllChildrenPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ChildrenDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByFirstNameShouldReturnAllChildrenByFirstNameWhenFirstNameExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ChildrenDTO> page = service.findAllPagedByFirstName(existingFirstName, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByFirstNameShouldThrowResourceNotFoundExceptionWhenFirstNameNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<ChildrenDTO> page = service.findAllPagedByFirstName(nonExistingFirstName, pageable);
            throw new ResourceNotFoundException("First name not found: " + nonExistingFirstName);
        });
    }

    @Test
    public void findAllPagedByLastNameShouldReturnAllChildrenPagedByLastNameWhenLastNameExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ChildrenDTO> page = service.findAllPagedByLastName(existingLastName, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByLastNameShouldThrowResourceNotFoundExceptionWhenLastNameNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<ChildrenDTO> page = service.findAllPagedByLastName(nonExistingLastName, pageable);
        });
    }

    @Test
    public void findAllPagedByAgeShouldReturnAllChildrenPagedByAgeWhenAgeExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ChildrenDTO> page = service.findAllPagedByAge(existingAge, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByAgeShouldThrowResourceNotFoundExceptionWhenAgeNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<ChildrenDTO> page = service.findAllPagedByAge(nonExistingAge, pageable);
            throw new ResourceNotFoundException("Age not found: " + nonExistingAge);
        });
    }

    @Test
    public void findAllPagedByCpfShouldReturnAllChildrenPageByCpfWhenCpfExists(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ChildrenDTO> page = service.findAllPagedByCpf(existingCpf, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByCpfShouldThrowResourceNotFoundExceptionWhenCpfNonExisting(){

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Page<ChildrenDTO> page = service.findAllPagedByCpf(nonExistingCpf, pageable);
            throw new ResourceNotFoundException("CPF not found: " + nonExistingCpf);
        });
    }

    @Test
    public void findByIdShouldReturnChildrenByIdWhenIdExists(){

        Optional<Children> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ChildrenDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(id, dto.getId());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ChildrenDTO dto = service.findById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        ChildrenDTO dto = Factory.createdChildrenDtoToInsert();

        dto = service.insert(dto);

        Assertions.assertEquals(10, repository.count());
    }
    
    @Test
    public void updateShouldSaveObjectWhenIdExists(){

        Optional<Children> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ChildrenDTO dto = Factory.createdChildrenDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertEquals(9, repository.count());
        Assertions.assertEquals(id, dto.getId());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            ChildrenDTO dto = Factory.createdChildrenDtoToUpdate();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExists(){

        Optional<Children> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(8, repository.count());
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
