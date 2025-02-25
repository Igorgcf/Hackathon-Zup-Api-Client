package br.com.hackthon.api_client.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceIT {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    String existsUsername;
    String nonExistingUsername;
    String existsEmail;
    String nonExistingEmail;
    String existsCpf;
    String nonExistingCpf;
    

    @BeforeEach
    void setUp() throws Exception{

        existsUsername = "HenriqueTechnology";
        nonExistingUsername = "PauloTechnology";
        existsEmail = "henrique@gmail.com";
        nonExistingEmail = "Paulo@gmail.com";
        existsCpf = "231.123.312-0";
        nonExistingCpf = "231.123.312-1";
    }

    @Test
    public void findAllPagedShouldReturnAllUserPaged() throws Exception {

        PageRequest pageable = PageRequest.of(0 , 12);

        ResultActions result = mockMvc.perform(get("/users", pageable));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].username").exists());
        result.andExpect(jsonPath("$.content[0].email").exists());
    }

    @Test
    public void findByUsernameShouldReturnAllUserByUsernameWhenUsernameExists() throws Exception {


        ResultActions result = mockMvc.perform(get("/users/username/{username}", existsUsername));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.[0].id").exists());
        result.andExpect(jsonPath("$.[0].username").exists());
        result.andExpect(jsonPath("$.[0].email").exists());
    }

    @Test
    public void findByUsernameShouldThrowResourceNotFoundExceptionWhenUsernameNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/username/{username}", nonExistingUsername));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByEmailShouldReturnAllUserByEmailWhenEmailExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/email/{email}", existsEmail));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.[0].id").exists());
        result.andExpect(jsonPath("$.[0].username").exists());
        result.andExpect(jsonPath("$.[0].email").exists());
    }

    @Test
    public void findByEmailShouldReturnStatusNotFoundWhenEmailNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/email/{email}", nonExistingEmail));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByCpfShouldReturnAllUserByCpfWhenCpfExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/cpf/{cpf}", existsCpf));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.[0].id").exists());
        result.andExpect(jsonPath("$.[0].username").exists());
        result.andExpect(jsonPath("$.[0].email").exists());
    }

    @Test
    public void findByCpfShouldReturnStatusNotFoundWhenCpfNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/cpf/{cpf}", nonExistingCpf));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnUserByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID ExistsId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/users/{id}", ExistsId));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.username").exists());
        result.andExpect(jsonPath("$.email").exists());
    }

    @Test
    public void findByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID nonExistingId = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/users/{id}", nonExistingId));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        UserDTO dto = Factory.createdUserDtoToInsert();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.username").exists());
        result.andExpect(jsonPath("$.email").exists());
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.username").exists());
        result.andExpect(jsonPath("$.email").exists());

        Assertions.assertEquals(5, repository.count());
    }

    @Test
    public void updateShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        UserDTO dto = Factory.createdUserDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteUserByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/users/{id}", id));

        result.andExpect(status().isOk());

        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void deleteByIdShouldReturnStatusNotFoundWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/users/{id}", id));

        result.andExpect(status().isNotFound());
    }
}
