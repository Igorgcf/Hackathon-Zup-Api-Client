package br.com.hackthon.api_client.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.repositories.*;
import br.com.hackthon.api_client.services.exceptions.*;
import br.com.hackthon.api_client.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
 
import java.time.*;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository repository;

    private String existsFirstName;
    private String nonExistingFirstName;
    private String existsLastName;
    private String nonExistingLastName;
    private int existsAge;
    private int nonExistingAge;
    private double existsIncome;
    private double nonExistingIncome;
    private String existsCpf;
    private String nonExistingCpf;
    private UUID nonExistingId;
    private String expectedDate;

    @BeforeEach
    void setUp() throws Exception{

        existsFirstName = "Ana";
        nonExistingFirstName = "Mauricio";
        existsLastName = "Albuquerque Pereira";
        nonExistingLastName = "Batista";
        existsAge = 64;
        nonExistingAge = 30;
        existsIncome = 3000.00;
        nonExistingIncome = 1000.00;
        existsCpf = "323.244.300-10";
        nonExistingCpf = "323.244.300-20";
        nonExistingId = UUID.randomUUID();
        expectedDate = LocalDate.of(1999, 1, 1).toString();

    }

    @Test
    public void findAllPagedShouldReturnAllPersonPaged() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons")
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.content").exists());
                    result.andExpect(jsonPath("$.totalElements").exists());
                    result.andExpect(jsonPath("$.content[0].firstName").value("Ana"));
                    result.andExpect(jsonPath("$.content[1].firstName").value("Lúcio"));
                    result.andExpect(jsonPath("$.content[2].firstName").value("Amanda"));
    }

    @Test
    public void findAllPagedByFirstNameShouldReturnAllPersonPagedByFirstNameWhenFirstNameExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/firstName/{firstName}", existsFirstName)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.content").exists());
                    result.andExpect(jsonPath("$.totalElements").exists());
                    result.andExpect(jsonPath("$.content[0].firstName").value("Ana"));
    }

    @Test
    public void findAllPagedByFirstNameShouldReturnNotFoundWhenFirstNameNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/firstName/{firstName}", nonExistingFirstName)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());

    }

    @Test
    public void findAllPagedByLastNameShouldReturnAllPersonPagedByLastNameWhenLastNameExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/lastName/{lastName}", existsLastName)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.content").exists());
                    result.andExpect(jsonPath("$.totalElements").exists());
                    result.andExpect(jsonPath("$.content[0].firstName").value("Ana"));
                    result.andExpect(jsonPath("$.content[0].lastName").value("Albuquerque Pereira"));
                    result.andExpect(jsonPath("$.content[0].dateBirth").value("1960-01-01"));
    }

    @Test
    public void findAllPagedByLastNameShouldReturnNotFoundWhenLastNameNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/lastName/{lastName}", nonExistingLastName)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());

    }

    @Test
    public void findAllPagedByAgeShouldReturnAllPersonPagedByAgeWhenAgeExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/age/{age}", existsAge)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.content").exists());
                    result.andExpect(jsonPath("$.totalElements").exists());
                    result.andExpect(jsonPath("$.content[0].firstName").value("Ana"));
                    result.andExpect(jsonPath("$.content[0].lastName").value("Albuquerque Pereira"));
                    result.andExpect(jsonPath("$.content[0].age").value(64));
    }

    @Test
    public void findAllPagedByAgeShouldReturnNotFoundWhenAgeNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/age/{age}", nonExistingAge)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedByIncomeShouldReturnAllPersonPagedByIncomeWhenIncomeExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/income/{income}", existsIncome)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.content").exists());
                    result.andExpect(jsonPath("$.totalElements").exists());
                    result.andExpect(jsonPath("$.content[0].firstName").value(existsFirstName));
                    result.andExpect(jsonPath("$.content[0].lastName").value(existsLastName));
                    result.andExpect(jsonPath("$.content[0].age").value(existsAge));
                    result.andExpect(jsonPath("$.content[0].income").value(existsIncome));
    }

    @Test
    public void findAllPagedByIncomeShouldReturnNotFoundWhenIncomeNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/income/{income}", nonExistingIncome)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedByCpfShouldReturnAllPersonPagedByCpfWhenCpfExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/cpf/{cpf}", existsCpf)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.content").exists());
                    result.andExpect(jsonPath("$.totalElements").exists());
                    result.andExpect(jsonPath("$.content[0].firstName").value(existsFirstName));
                    result.andExpect(jsonPath("$.content[0].lastName").value(existsLastName));
                    result.andExpect(jsonPath("$.content[0].age").value(existsAge));
                    result.andExpect(jsonPath("$.content[0].cpf").value(existsCpf));
    }

    @Test
    public void findAllPagedByCpfShouldReturnNotFoundWhenCpfNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/cpf/{cpf}", nonExistingCpf)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnPersonByIdWhenIdExists() throws Exception{

        Optional<Person> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/persons/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.id").exists());
                    result.andExpect(jsonPath("$.firstName").value(existsFirstName));
                    result.andExpect(jsonPath("$.lastName").value(existsLastName));
                    result.andExpect(jsonPath("$.age").value(existsAge));
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/persons/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSavePersonWhenCorrectStructure() throws Exception{

        PersonDTO dto = Factory.createdPersonDtoToInsert();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/persons")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                            result.andExpect(jsonPath("$.id").exists());
                            result.andExpect(jsonPath("$.firstName").value("Luana"));
                            result.andExpect(jsonPath("$.lastName").value("Fernanda Souza"));
                            result.andExpect(jsonPath("$.age").value(26));
                            result.andExpect(jsonPath("income").value(60000.00));
    }

    @Test
    public void updateShouldSavePersonWhenIdExistsAndCorrectStructure() throws Exception{

        Optional<Person> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        PersonDTO dto = Factory.createdPersonDtoUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/persons/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                            result.andExpect(jsonPath("$.id").exists());
                            result.andExpect(jsonPath("$.firstName").value("Kaio"));
                            result.andExpect(jsonPath("$.lastName").value("Luiz Felipe"));
                            result.andExpect(jsonPath("$.dateBirth").value(expectedDate));
                            result.andExpect(jsonPath("$.age").value(25));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdNonExisting() throws Exception{

        PersonDTO dto = Factory.createdPersonDtoUpdate();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/persons/{id}", nonExistingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeletePersonWhenIdExists() throws Exception{

        Optional<Person> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/persons/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isOk());

                    Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void deleteByIdShouldReturnNotFoundWhenIdNonExisting() throws Exception{

        ResultActions result = mockMvc.perform(delete("/persons/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
                    result.andExpect(status().isNotFound());
    }
}

