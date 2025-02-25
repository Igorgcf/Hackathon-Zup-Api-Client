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
public class ChildrenResourceIT {

    @Autowired
    private ChildrenServiceImpl service;

    @Autowired
    private ChildrenRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

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

        existingFirstName = "Luana";
        nonExistingFirstName = "Fabricio";
        existingLastName = "Alric Barbosa";
        nonExistingLastName = "José Lima";
        existingAge = 19;
        nonExistingAge = 27;
        existingCpf = "742.218.630-56";
        nonExistingCpf = "742.218.630-57";
    }

    @Test
    public void findAllPagedShouldReturnAllChildrenPaged() throws Exception{

        ResultActions result = mockMvc.perform(get("/childrens"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].firstName").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());
    }

    @Test
    public void findAllPagedByFirstNameShouldReturnAllChildrenPagedByFirstNameWhenFirstNameExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/childrens/firstName/{firstName}", existingFirstName));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].firstName").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());
    }

    @Test
    public void findAllPagedByFirstNameShouldReturnStatusNotFoundWhenFirstNameNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/childrens/firstName/{firstName}", nonExistingFirstName));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedByLastNameShouldReturnAllChildrenPagedByLastNameWhenLastNameExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/childrens/lastName/{lastName}", existingLastName));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].firstName").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());
    }

    @Test
    public void findAllPagedByLastNameShouldReturnStatusNotFoundWhenLastNameNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/childrens/lastName/{lastName}", nonExistingLastName));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedByAgeShouldReturnAllChildrenPagedByAgeWhenAgeExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/childrens/age/{age}", existingAge));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].firstName").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());
    }

    @Test
    public void findAllPagedByAgeShouldReturnStatusNotFoundWhenAgeNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/childrens/age/{age}", nonExistingAge));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedByCpfShouldReturnAllChildrenPagedByCpfWhenCpfExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/childrens/cpf/{cpf}", existingCpf));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].firstName").exists());
        result.andExpect(jsonPath("$.content[0].lastName").exists());
    }

    @Test
    public void findAllPagedByCpfShouldReturnStatusNotFoundWhenCpfNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/childrens/cpf/{cpf}", nonExistingCpf));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnChildrenByIdtWhenIdExists() throws Exception {

        Optional<Children> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/childrens/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.firstName").exists());
        result.andExpect(jsonPath("$.lastName").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/childrens/{id}", id));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        ChildrenDTO dto = Factory.createdChildrenDtoToInsert();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/childrens")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.firstName").exists());
        result.andExpect(jsonPath("$.lastName").exists());

        Assertions.assertEquals(10, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExists() throws Exception {

        Optional<Children> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ChildrenDTO dto = Factory.createdChildrenDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/childrens/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.firstName").exists());
        result.andExpect(jsonPath("$.lastName").exists());

        Assertions.assertEquals(9, repository.count());
        }

        @Test
        public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

            UUID id = UUID.randomUUID();

            ChildrenDTO dto = Factory.createdChildrenDtoToUpdate();

            String jsonBody = mapper.writeValueAsString(dto);

            ResultActions result = mockMvc.perform(put("/childrens/{id}", id)
                    .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isNotFound());
        }

        @Test
        public void deleteByIdShouldDeleteObjectWhenIdExists() throws Exception {

            Optional<Children> obj = repository.findAll().stream().findFirst();

            UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

            ResultActions result = mockMvc.perform(delete("/childrens/{id}", id));

            result.andExpect(status().isOk());
            Assertions.assertEquals(8, repository.count());
        }

        @Test
        public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

            UUID id = UUID.randomUUID();

            ResultActions result = mockMvc.perform(delete("/childrens/{id}", id));

            result.andExpect(status().isNotFound());
        }
    }
