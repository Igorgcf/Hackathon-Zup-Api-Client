package br.com.hackthon.api_client.resources;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.services.impl.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserServiceImpl service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable){

        Page<UserDTO> page = service.findAllPaged(pageable);
        if(!page.isEmpty()){
            for(UserDTO userDTO : page.toList()){
                userDTO.add(linkTo(methodOn(UserResource.class).findById(userDTO.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<List<UserDTO>> findByUserName(@PathVariable String username){

        List<UserDTO> list = service.findByUsername(username);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<List<UserDTO>> findByEmail(@PathVariable String email){

        List<UserDTO> list = service.findByEmail(email);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<List<UserDTO>> findByCpf(@PathVariable String cpf){

        List<UserDTO> list = service.findByCpf(cpf);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id){

        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@JsonView(UserDTO.UserView.registrationPost.class)
                                          @Validated(UserDTO.UserView.registrationPost.class)
                                          @RequestBody UserDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id,
                                          @JsonView(UserDTO.UserView.userPut.class)
                                          @Validated(UserDTO.UserView.userPut.class)
                                          @RequestBody UserDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Object> passwordUpdate(@PathVariable UUID id, @RequestBody UserDTO dto){

        service.passwordUpdate(id, dto);
        return ResponseEntity.ok().body("Password update successfully.");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("User deleted successfully.");
    }
}
