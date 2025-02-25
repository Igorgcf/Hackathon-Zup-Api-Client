package br.com.hackthon.api_client.dto;

import br.com.hackthon.api_client.entities.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.hateoas.*;

import java.time.*;
import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDTO extends RepresentationModel<FamilyDTO>  {

    private UUID id;

    @NotNull(message = "Last name field is mandatory.")
    @Size(min = 2, max = 70, message = "Minimum characters 2 and maximum 70.")
    private String lastName;

    @NotNull(message = "State field is mandatory.")
    @Size(min = 2, max = 70, message = "Minimum characters 2 and maximum 70.")
    private String state;

    @NotNull(message = "City field is mandatory.")
    @Size(min = 2, max = 70, message = "Minimum characters 2 and maximum 70.")
    private String city;

    @NotNull(message = "Street field is mandatory.")
    @Size(min = 2, max = 170, message = "Minimum characters 2 and maximum 170.")
    private String street;

    @NotNull(message = "Address number field is mandatory.")
    @Min(value = 1, message = "Minimum characters 1.")
    @Max(value = 9999, message = "Maximum characters 9999.")
    private Integer addressNumber;

    private LocalDateTime collectionDate;

    private LocalDateTime lastUpdateDate;

    private List<PersonDTO> members = new ArrayList<>();

    private List<ChildrenDTO> children = new ArrayList<>();

    public FamilyDTO(){
    }

    public FamilyDTO(UUID id, String lastName, String state, String city, String street, Integer addressNumber, LocalDateTime collectionDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.lastName = lastName;
        this.state = state;
        this.city = city;
        this.street = street;
        this.addressNumber = addressNumber;
        this.collectionDate = collectionDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public FamilyDTO(Family entity){

        id = entity.getId();
        lastName = entity.getLastName();
        state = entity.getState();
        city = entity.getCity();
        street = entity.getStreet();
        addressNumber = entity.getAddressNumber();
        collectionDate = entity.getCollectionDate();
        lastUpdateDate = entity.getLastUpdateDate();

    }

    public FamilyDTO(Family entity, List<Person> members, List<Children> children){

        this(entity);

        if(members != null){
            members.forEach(x -> this.members.add(new PersonDTO(x)));
        }

        if(children != null){
            children.forEach(x -> this.children.add(new ChildrenDTO(x)));
        }

    }

}
