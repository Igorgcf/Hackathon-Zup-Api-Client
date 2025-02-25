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
public class RoleResourceIT {

    @Autowired
    private RoleServiceImpl service;

    @Autowired
    private RoleRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    String existingAuthority;
    String nonExistingAuthority;

    @BeforeEach
    void setUp() throws Exception{
        existingAuthority = "CEO";
        nonExistingAuthority = "Seller";
    }

    @Test
    public void findAllPagedShouldReturnAllRolesPaged() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].authority").exists());
    }

    @Test
    public void findAllPagedByAuthorityShouldReturnAllRolesPagedByAuthorityWhenAuthorityExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles/authority/{authority}", existingAuthority));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].authority").exists());
    }

    @Test
    public void findAllPagedByAuthorityShouldReturnStatusNotFoundWhenAuthorityNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles/authority/{authority}", nonExistingAuthority));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnRoleByIdWhenIdExists() throws Exception {

        Optional<Role> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/roles/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/roles/{id}", id));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception{

        RoleDTO dto = Factory.createdRoleDtoToInsert();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/roles")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExists() throws Exception {

        Optional<Role> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        RoleDTO dto = Factory.createdRoleDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        RoleDTO dto = Factory.createdRoleDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteRoleByIdWhenIdExists() throws Exception {

        Optional<Role> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/roles/{id}", id));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/roles/{id}", id));

        result.andExpect(status().isNotFound());
    }
}
