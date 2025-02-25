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
public class ChildrenDTO extends RepresentationModel<ChildrenDTO> {

    private UUID id;

    @NotBlank(message = "First name field is mandatory and blanks are not allowed.")
    @Size(min = 2, max = 70, message = "Minimum characters 2 and maximum 70.")
    private String firstName;

    @NotNull(message = "Last name field is mandatory.")
    @Size(min = 2, max = 70, message = "Minimum characters 2 and maximum 70.")
    private String lastName;

    @NotNull(message = "The date birth field is mandatory. Ex: yyyy-MM-dd")
    private LocalDate dateBirth;

    @NotNull(message = "Age field is mandatory.")
    @Min(value = 0, message = "Minimum value 0.")
    @Max(value = 170, message = "Maximum value 170.")
    private Integer age;

    @CpfConstraint(message = "CPF is already in use.")
    @NotBlank(message = "The CPF field is mandatory and blank spaces are not allowed.")
    @Size(min = 10, max = 20, message = "Minimum characters 10 and maximum 20.")
    private String cpf;

    private LocalDateTime collectionDate;

    private FamilyDTO family;

    public ChildrenDTO(){
    }

    public ChildrenDTO(UUID id, String firstName, String lastName, LocalDate dateBirth, Integer age, String cpf, LocalDateTime collectionDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.age = age;
        this.cpf = cpf;
        this.collectionDate = collectionDate;
    }

    public ChildrenDTO(Children entity){

        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        dateBirth = entity.getDateBirth();
        age = entity.getAge();
        cpf = entity.getCpf();
        collectionDate = entity.getCollectionDate();
    }

    public ChildrenDTO(Children entity, Family family) {

        this(entity);

        if (family != null) {
            this.family = new FamilyDTO();
            this.family.setId(family.getId());
            this.family.setLastName(family.getLastName());
            this.family.setState(family.getState());
            this.family.setCity(family.getCity());
            this.family.setStreet(family.getStreet());
            this.family.setAddressNumber(family.getAddressNumber());

            for(Children children : family.getChildren()){
                this.family.getChildren().add(new ChildrenDTO(children));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChildrenDTO that = (ChildrenDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
