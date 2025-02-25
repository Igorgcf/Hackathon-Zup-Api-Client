package br.com.hackthon.api_client.resources;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.services.impl.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/childrens")
public class ChildrenResource {

    @Autowired
    private ChildrenServiceImpl service;

    @GetMapping
    public ResponseEntity<Page<ChildrenDTO>> findAllPaged(Pageable pageable){

        Page<ChildrenDTO> page = service.findAllPaged(pageable);
        if(!page.isEmpty()){
            for(ChildrenDTO childrenDTO : page.toList()){
                childrenDTO.add(linkTo(methodOn(ChildrenResource.class).findById(childrenDTO.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/firstName/{firstName}")
    public ResponseEntity<Page<ChildrenDTO>> findAllPagedByFirstName(@PathVariable String firstName, Pageable pageable){

        Page<ChildrenDTO> page = service.findAllPagedByFirstName(firstName, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/lastName/{lastName}")
    public ResponseEntity<Page<ChildrenDTO>> findAllPagedByLastName(@PathVariable String lastName, Pageable pageable){

        Page<ChildrenDTO> page = service.findAllPagedByLastName(lastName, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/age/{age}")
    public ResponseEntity<Page<ChildrenDTO>> findAllPagedByAge(@PathVariable int age, Pageable pageable){

        Page<ChildrenDTO> page = service.findAllPagedByAge(age, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<Page<ChildrenDTO>> findAllPagedByCpf(@PathVariable String cpf, Pageable pageable){

        Page<ChildrenDTO> page = service.findAllPagedByCpf(cpf, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChildrenDTO> findById(@PathVariable UUID id){

        ChildrenDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ChildrenDTO> insert(@Valid @RequestBody ChildrenDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ChildrenDTO> update(@PathVariable UUID id, @Valid @RequestBody ChildrenDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Children deleted successfully.");
    }
}
