package br.com.hackthon.api_client.resources;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.services.impl.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/persons")
public class PersonResource {

    @Autowired
    private PersonServiceImpl service;

    @GetMapping
    public ResponseEntity<Page<PersonDTO>> findAllPaged(Pageable pageable){

        Page<PersonDTO> page = service.findAllPaged(pageable);

        if(!page.isEmpty()){
            for(PersonDTO personDTO : page.toList()){
                personDTO.add(linkTo(methodOn(PersonResource.class).findById(personDTO.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/firstName/{firstName}")
    public ResponseEntity<Page<PersonDTO>> findAllPagedByFirstName(@PathVariable String firstName, Pageable pageable){

        Page<PersonDTO> page = service.findAllPagedByFirstName(firstName, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/lastName/{lastName}")
    public ResponseEntity<Page<PersonDTO>> findAllPagedByLastName(@PathVariable String lastName, Pageable pageable){

        Page<PersonDTO> page = service.findAllPagedByLastName(lastName, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/age/{age}")
    public ResponseEntity<Page<PersonDTO>> findAllPagedByAge(@PathVariable int age, Pageable pageable){

        Page<PersonDTO> page = service.findAllPagedByAge(age, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/income/{income}")
    public ResponseEntity<Page<PersonDTO>> findAllPagedByIncome(@PathVariable double income, Pageable pageable){

        Page<PersonDTO> page = service.findAllPagedByIncome(income, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<Page<PersonDTO>> findAllPagedByCpf(@PathVariable String cpf, Pageable pageable){

        Page<PersonDTO> page = service.findAllPagedByCpf(cpf, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable UUID id){

        PersonDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> insert(@Valid @RequestBody PersonDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable UUID id, @Valid @RequestBody PersonDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Person deleted successfully.");
    }
}
