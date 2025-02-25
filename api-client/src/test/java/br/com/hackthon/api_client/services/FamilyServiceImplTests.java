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
public class FamilyServiceImplTests {

    @Autowired
    private FamilyServiceImpl service;

    @Autowired
    private FamilyRepository repository;

    @Test
    public void findAllPagedShouldReturnAllFamilyPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<FamilyDTO> page = service.findAllPaged(pageable);

        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByLastNameShouldReturnAllFamilyPagedByLastNameWhenLastNameExists(){

        String lastName = "Pereira";

        PageRequest pageable = PageRequest.of(0, 12);

        Page<FamilyDTO> page = service.findAllPagedByLastName(lastName, pageable);

        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByLastNameShouldThrowResourceNotFoundExceptionWhenLastNameNonExisting(){

        String lastName = "Augusto";

        PageRequest pageable = PageRequest.of(0 ,12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<FamilyDTO> page = service.findAllPagedByLastName(lastName, pageable);
            if(page.isEmpty()){
                throw new ResourceNotFoundException("Last name not found: " + lastName);
            }
        });
    }

    @Test
    public void findAllPagedByStateShouldReturnAllFamilyPagedByStateWhenStateExists(){

        String state = "SP";

        PageRequest pageable = PageRequest.of(0, 12);

        Page<FamilyDTO> page = service.findByState(state, pageable);

        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByStateShouldThrowResourceNotFoundExceptionWhenStateNonExisting(){

        String state = "PA";

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<FamilyDTO> page = service.findByState(state, pageable);
            if(page.isEmpty()){
                throw new ResourceNotFoundException("State not found: " + state);
            }
        });
    }

    @Test
    public void findAllPagedByCityShouldReturnAllFamilyPagedByCityWhenCityExists(){

        String city = "São Paulo";

        PageRequest pageable = PageRequest.of(0 ,12);

        Page<FamilyDTO> page = service.findByCity(city, pageable);

        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByCityShouldThrowResourceNotFoundExceptionWhenCityNonExisting(){

        String city = "São Roque";

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<FamilyDTO> page = service.findByCity(city, pageable);
            if(page.isEmpty()){
                throw new ResourceNotFoundException("City not found: " + city);
            }
        });
    }

    @Test
    public void findByIdShouldReturnFamilyByIdWhenIdExists(){

        Optional<Family> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        FamilyDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(id, dto.getId());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() {

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            FamilyDTO dto = service.findById(id);
            if (dto == null) {
                throw new ResourceNotFoundException("Id not found: " + id);
            }
        });
    }

     @Test
     public void insertShouldSaveFamilyWhenCorrectStructure(){

        FamilyDTO dto = Factory.createdFamilyDtoToInsert();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(6, repository.count());
        }

     @Test
     public void updateShouldSaveObjectWhenIdExists(){

        Optional<Family> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        FamilyDTO dto = Factory.createdFamilyDtoToInsert();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(id, dto.getId());
        Assertions.assertEquals(5, repository.count());
     }

     @Test
     public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            FamilyDTO dto = Factory.createdFamilyDtoToUpdate();
            dto = service.update(id, dto);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
     }

     @Test
     public void deleteByIdShouldDeleteFamilyByIdWhenIdExisting(){

        Optional<Family> obj = repository.findAll().stream().findFirst();

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
