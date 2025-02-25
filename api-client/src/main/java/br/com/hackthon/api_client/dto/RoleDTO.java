package br.com.hackthon.api_client.dto;

import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.validation.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.hateoas.*;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RoleDTO extends RepresentationModel<RoleDTO> {

    public interface RoleView{
        public static interface RegistrationPost { }
        public static interface RolePut { }
    }

    @JsonView({UserDTO.UserView.registrationPost.class, UserDTO.UserView.userPut.class})
    private UUID id;

    @Size(min = 4, max = 70, message = "Minimum characters 4 and maximum 70.", groups = {RoleView.RegistrationPost.class, RoleView.RolePut.class})
    @NotNull(message = "The authority field is mandatory.", groups = {RoleView.RegistrationPost.class, RoleView.RolePut.class})
    @AuthorityConstraint(message = "Invalid authority, already exists.", groups = {RoleView.RegistrationPost.class, RoleView.RolePut.class})
    @JsonView({RoleView.RegistrationPost.class, RoleView.RolePut.class, UserDTO.UserView.registrationPost.class, UserDTO.UserView.userPut.class})
    private String authority;

    public RoleDTO() {
    }

    public RoleDTO(UUID id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO(Role entity) {

        id = entity.getId();
        authority = entity.getAuthority();
    }
}

