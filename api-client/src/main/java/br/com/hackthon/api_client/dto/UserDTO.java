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
public class UserDTO extends RepresentationModel<UserDTO> {

    public interface UserView{
        public static interface passwordPut{ }
        public static interface registrationPost { }
        public static interface userPut { }
    }

    private UUID id;

    @Size(min = 4, max = 50, message = "Minimum characters 4 and maximum 50.", groups = UserView.registrationPost.class)
    @NotBlank(message = "The username field is mandatory and blanks are not allowed.", groups = UserView.registrationPost.class)
    @UsernameConstraint(message = "Invalid username, already exists.", groups = UserView.registrationPost.class)
    @JsonView({UserView.registrationPost.class})
    private String username;

    @Size(min = 10, max = 50, message = "Minimum characters 10 and maximum 50.", groups = UserView.registrationPost.class)
    @Email(message = "The email field must follow an acceptable standard.", groups = UserView.registrationPost.class)
    @NotBlank(message = "The e-mail field is mandatory and blanks are not allowed.", groups = UserView.registrationPost.class)
    @EmailConstraint(message = "Invalid email, already exists.", groups = UserView.registrationPost.class)
    @JsonView(UserView.registrationPost.class)
    private String email;

    @Size(min = 7, max = 70, message = "Minimum characters 7 and maximum 70.", groups = {UserView.registrationPost.class, UserView.passwordPut.class})
    @NotBlank(message = "The password field is mandatory and blanks are not allowed.", groups = {UserView.registrationPost.class, UserView.passwordPut.class})
    @JsonView({UserView.registrationPost.class, UserView.passwordPut.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Size(min = 7, max = 70, message = "Minimum characters 7 and maximum 70.", groups = UserView.passwordPut.class)
    @NotBlank(message = "The Old password field is mandatory and blanks are not allowed.", groups = UserView.passwordPut.class)
    @JsonView(UserView.passwordPut.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String oldPassword;

    @NotNull(message = "The full name is mandatory.", groups = {UserView.registrationPost.class, UserView.userPut.class})
    @Size(min = 2, max = 100, message = "Minimum characters 2 and maximum 100.", groups = {UserView.registrationPost.class, UserView.userPut.class})
    @JsonView({UserView.registrationPost.class, UserView.userPut.class})
    private String fullName;

    @Size(min = 10, max = 20, message = "Minimum characters 9 and maximum 20.", groups = {UserView.registrationPost.class, UserView.userPut.class})
    @JsonView({UserView.registrationPost.class, UserView.userPut.class})
    private String phoneNumber;

    @Size(min = 10, max = 20, message = "Minimum characters 10 and maximum 20.", groups = {UserView.registrationPost.class, UserView.userPut.class})
    @NotBlank(message = "The CPF field is mandatory and blanks are not allowed.", groups = {UserView.registrationPost.class, UserView.userPut.class})
    @CpfConstraint(message = "Invalid CPF, already exists.", groups = {UserView.registrationPost.class, UserView.userPut.class})
    @JsonView({UserView.registrationPost.class, UserView.userPut.class})
    private String cpf;

    @JsonView({UserView.registrationPost.class, UserView.userPut.class})
    private String imageUrl;


    private LocalDateTime collectionDate;
    private LocalDateTime lastUpdateDate;

    @JsonView({UserView.registrationPost.class, UserView.userPut.class})
    private List<RoleDTO> roles = new ArrayList<>();

    public UserDTO(){
    }

    public UserDTO(UUID id, String username, String email, String password, String fullName, String phoneNumber, String cpf,
                   String imageUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.imageUrl = imageUrl;
    }

    public UserDTO(User entity){

        id = entity.getId();
        username = entity.getUsername();
        email = entity.getEmail();
        password = entity.getPassword();
        fullName = entity.getFullName();
        phoneNumber = entity.getPhoneNumber();
        cpf = entity.getCpf();
        imageUrl = entity.getImageUrl();
        collectionDate = entity.getCollectionDate();
        lastUpdateDate = entity.getLastUpdateDate();
    }

    public UserDTO(User entity, List<Role> roles){

        this(entity);
        roles.forEach(x -> this.roles.add(new RoleDTO(x)));
    }
}
