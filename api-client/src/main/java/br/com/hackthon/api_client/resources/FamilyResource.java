package br.com.hackthon.api_client.resources;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.repositories.*;
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
@RequestMapping(value = "/families")
public class FamilyResource {

    @Autowired
    private FamilyServiceImpl service;

    @Autowired
    private FamilyRepository repository;

    @GetMapping
    public ResponseEntity<Page<FamilyDTO>> findAllPaged(Pageable pageable) {

        Page<FamilyDTO> page = service.findAllPaged(pageable);
        if (!page.isEmpty()) {
            for (FamilyDTO family : page.toList()) {
                family.add(linkTo(methodOn(FamilyResource.class).findById(family.getId())).withSelfRel());
            }

        }
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/lastName/{lastName}")
    public ResponseEntity<Page<FamilyDTO>> findAllPagedByLastName(@PathVariable String lastName, Pageable pageable){

        Page<FamilyDTO> page = service.findAllPagedByLastName(lastName, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/state/{state}")
    public ResponseEntity<Page<FamilyDTO>> findByState(@PathVariable String state, Pageable pageable){

        Page<FamilyDTO> page = service.findByState(state,pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/city/{city}")
    public ResponseEntity<Page<FamilyDTO>> findByCity(@PathVariable String city, Pageable pageable){

        Page<FamilyDTO> page = service.findByCity(city, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FamilyDTO> findById(@PathVariable UUID id){

        FamilyDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<FamilyDTO> insert(@RequestBody @Valid FamilyDTO dto){

        dto = service.insert(dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FamilyDTO> update(@PathVariable UUID id, @RequestBody @Valid FamilyDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Family deleted successfully.");
    }
}

