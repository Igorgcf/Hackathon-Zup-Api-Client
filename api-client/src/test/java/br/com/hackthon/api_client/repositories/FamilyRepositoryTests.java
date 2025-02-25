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
public class FamilyRepositoryTests {

    @Autowired
    private FamilyRepository  repository;

    @Test
    public void findAllPagedShouldReturnAllFamilyPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Family> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllByLastNameShouldReturnAllFamilyByLastNameWhenLastNameExists(){

        String lastName = "Pereira";

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Family> page = repository.findAllPagedByLastNameContainingIgnoreCase(lastName, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByLastNameShouldThrowResourceNotFoundExceptionWhenLastNameNonExisting(){


        String lastName = "Augusto";

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<Family> page = repository.findAllPagedByLastNameContainingIgnoreCase(lastName, pageable);
            if(page.isEmpty()){
                throw new ResourceNotFoundException("last name not found: " + lastName);
            }
        });
    }

    @Test
    public void findAllPagedByStateShouldReturnAllFamilyByStateWhenStateExists(){

        String state = "SP";

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Family> page = repository.findAllPagedByStateContainingIgnoreCase(state, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByStateShouldThrowResourceNotFoundExceptionWhenStateNonExisting(){

        String state = "PA";

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<Family> page = repository.findAllPagedByStateContainingIgnoreCase(state, pageable);
            if(page.isEmpty()){
                throw new ResourceNotFoundException("State not found: " + state);
            }
        });

    }

    @Test
    public void findAllPagedByCityShouldReturnAllFamilyPagedByCityWhenCityExists(){

        String city = "São Paulo";

        PageRequest pageable = PageRequest.of(0, 12);

        Page<Family> page = repository.findAllPagedByCityContainingIgnoreCase(city, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findAllPagedByCityShouldThrowResourceNotFoundExceptionWhenCityNonExisting(){

        String city = "São Roque";

        PageRequest pageable = PageRequest.of(0, 12);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Page<Family> page = repository.findAllPagedByCityContainingIgnoreCase(city, pageable);

            if(page.isEmpty()){
                throw new ResourceNotFoundException("City not found: " + city);
            }
        });
    }

    @Test
    public void findByIdShouldReturnFamilyByIdWhenIdExists(){

        Optional<Family> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Family> optional = repository.findById(id);

        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id = UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {

            Optional<Family> obj = repository.findById(id);
            if(obj.isEmpty()){
                throw new ResourceNotFoundException("Id not found: " + id);
            }
        });
    }

    @Test
    public void saveShouldSaveEntityWhenCorrectStructure(){

        Family entity = Factory.createdFamily();

        repository.save(entity);

        Assertions.assertEquals(repository.count(), 6);
    }

    @Test
    public void deleteByIdShouldDeleteFamilyByIdWhenIdExists(){

        Optional<Family> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<Family> optional = repository.findById(id);

        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        UUID id =  UUID.randomUUID();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            repository.deleteById(id);
            throw new ResourceNotFoundException("Id not found: " + id);
        });
    }
}
