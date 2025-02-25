package br.com.hackthon.api_client.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "tb_children")
public class Children {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false, length = 70)
    private String firstName;

    @Column(nullable = false, length = 170)
    private String lastName;

    @Column(nullable = false, name = "dateBirth")
    private LocalDate dateBirth;

    @Column(nullable = false)
    private Integer age;

    @Column(unique = true)
    private String cpf;

    @Column(nullable = false)
    private LocalDateTime collectionDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "family_id")
    private Family family;

    public Children(){
    }

    public Children(UUID id, String firstName, String lastName, LocalDate dateBirth, Integer age, String cpf, LocalDateTime collectionDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.age = age;
        this.cpf = cpf;
        this.collectionDate = collectionDate;
    }

}
