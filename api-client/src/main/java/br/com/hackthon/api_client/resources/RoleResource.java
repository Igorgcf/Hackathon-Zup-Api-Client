package br.com.hackthon.api_client.resources;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.services.impl.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/roles")
public class RoleResource {

    @Autowired
    private RoleServiceImpl service;

    @GetMapping
    public ResponseEntity<Page<RoleDTO>> findAllPaged(Pageable pageable){

        Page<RoleDTO> page = service.findAllPaged(pageable);
        if(!page.isEmpty()){
            for(RoleDTO roleDTO : page.toList()){
                roleDTO.add(linkTo(methodOn(RoleResource.class).findById(roleDTO.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/authority/{authority}")
    public ResponseEntity<Page<RoleDTO>> findAllPagedByAuthority(@PathVariable String authority, Pageable pageable){

        Page<RoleDTO> page = service.findAllPagedByAuthority(authority, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable UUID id){

        RoleDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> insert(@JsonView(RoleDTO.RoleView.RegistrationPost.class)
                                          @Validated(RoleDTO.RoleView.RegistrationPost.class)
                                          @RequestBody RoleDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable UUID id,
                                          @JsonView(RoleDTO.RoleView.RolePut.class)
                                          @Validated(RoleDTO.RoleView.RolePut.class)
                                          @RequestBody RoleDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Role deleted successfully.");
    }

}
