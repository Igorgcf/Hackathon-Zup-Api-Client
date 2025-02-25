package br.com.hackthon.api_client.dto;

import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.validation.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.hateoas.*;

import java.time.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PersonDTO extends RepresentationModel<PersonDTO> {

    private UUID id;

    @NotBlank(message = "First name field is mandatory and blanks are not allowed.")
    @Size(min = 2, max = 70, message = "Minimum characters 2 and maximum 70.")
    private String firstName;

    @NotNull(message = "Last name field is mandatory and blanks are not allowed.")
    @Size(min = 2, max = 70, message = "Minimum characters 2 and maximum 70.")
    private String lastName;

    @NotNull(message = "The date birth field is mandatory. Ex: yyyy-MM-dd")
    private LocalDate dateBirth;

    @NotNull(message = "Age field is mandatory.")
    @Min(value = 1, message = "Minimum value 1.")
    @Max(value = 170, message = "Maximum value 170.")
    private Integer age;

    @CpfConstraint(message = "Invalid, existing CPF.")
    @NotNull(message = "CPF field is mandatory.")
    @Size(min = 10, max = 20, message = "Minimum characters 10 and maximum 20.")
    private String cpf;

    private Double income;

    private LocalDateTime collectionDate;

    private FamilyDTO family;

    public PersonDTO(){
    }

    public PersonDTO(UUID id, String firstName, String lastName, LocalDate dateBirth, Integer age,
                     String cpf, Double income, LocalDateTime collectionDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.age = age;
        this.cpf = cpf;
        this.income = income;
        this.collectionDate = collectionDate;
    }

    public PersonDTO(Person entity){

        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        dateBirth = entity.getDateBirth();
        age = entity.getAge();
        cpf = entity.getCpf();
        income = entity.getIncome();
        collectionDate = entity.getCollectionDate();

    }

    public PersonDTO(Person entity, Family family){

        this(entity);

        if (family != null) {
            this.family = new FamilyDTO();
            this.family.setId(family.getId());
            this.family.setLastName(family.getLastName());
            this.family.setState(family.getState());
            this.family.setCity(family.getCity());
            this.family.setStreet(family.getStreet());
            this.family.setAddressNumber(family.getAddressNumber());

            this.family.getMembers().add(new PersonDTO(entity));

            for(Children children : family.getChildren()){
                this.family.getChildren().add(new ChildrenDTO(children));
            }
        }

    }
}
