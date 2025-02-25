package br.com.hackthon.api_client.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.repositories.*;
import br.com.hackthon.api_client.services.exceptions.*;
import br.com.hackthon.api_client.services.impl.*;
import br.com.hackthon.api_client.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FamilyResourceIT {

    @Autowired
    private FamilyServiceImpl service;

    @Autowired
    private FamilyRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    String ExistingLastName;
    String nonExistingLastName;
    String existingState;
    String nonExistingState;
    String existingCity;
    String nonExistingCity;

    @BeforeEach
    void setUp() throws Exception{

        ExistingLastName = "Souza";
        nonExistingLastName = "Cesar";
        existingState = "SP";
        nonExistingState = "PA";
        existingCity = "São Paulo";
        nonExistingCity = "São Roque";

    }

    @Test
    public void findAllPagedShouldReturnAllFamiliesPaged() throws Exception {

        ResultActions result = mockMvc.perform(get("/families?page=0&size=12"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());
    }

    @Test
    public void findAllPagedByLastNameShouldReturnAllFamiliesByLastNameWhenLastNameExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/families/lastName/{lastName}", ExistingLastName));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());
    }
    
    @Test
    public void findAllPagedByLastNameShouldReturnStatusNotFoundWhenLastNameNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/families/lastName/{lastName}", nonExistingLastName));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedByStateShouldReturnAllFamiliesByStateWhenStateExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/families/state/{state}", existingState));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());

    }

    @Test
    public void findAllPagedByStateShouldReturnStatusNotFoundWhenStateNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/families/state/{state}", nonExistingState));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedByCityShouldReturnAllFamiliesPagedByCityWhenCityExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/families/city/{city}", existingCity));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());
    }
    
    @Test
    public void findAllPagedByCityShouldReturnStatusNotFoundWhenCityNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/families/city/{city}", nonExistingCity));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnFamilyByIdWhenIdExists() throws Exception {

        Optional<Family> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/families/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.lastName").exists());
        result.andExpect(jsonPath("$.state").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/families/{id}", id));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        FamilyDTO dto = Factory.createdFamilyDtoToInsert();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/families")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.lastName").exists());
        result.andExpect(jsonPath("$.state").exists());

    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception {

        Optional<Family> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        FamilyDTO dto = Factory.createdFamilyDtoToInsert();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/families/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.lastName").exists());
        result.andExpect(jsonPath("$.state").exists());

        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExisting()throws Exception{

        UUID id = UUID.randomUUID();

        FamilyDTO dto = Factory.createdFamilyDtoToInsert();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/families/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExists() throws Exception {

        Optional<Family> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/families/{id}", id));

            result.andExpect(status().isOk());
            Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/families/{id}", id));

        result.andExpect(status().isNotFound());
    }
}
