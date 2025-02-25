package br.com.hackthon.api_client.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.time.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "tb_family")
public class Family implements Serializable {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    @Column(nullable = false, length = 70)
    private String lastName;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 170)
    private String street;

    @Column(name = "address_number", nullable = false)
    private Integer addressNumber;

    @Column(nullable = false)
    private LocalDateTime collectionDate;

    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "family", cascade = {CascadeType.ALL, CascadeType.PERSIST})
    List<Person> members = new ArrayList<>();

    @OneToMany(mappedBy = "family", cascade = {CascadeType.ALL, CascadeType.PERSIST})
    List<Children> children = new ArrayList<>();

    public Family(){

    }

    public Family(UUID id, String lastName, String state, String city, String street, Integer addressNumber, LocalDateTime collectionDate) {
        this.id = id;
        this.lastName = lastName;
        this.state = state;
        this.city = city;
        this.street = street;
        this.addressNumber = addressNumber;
        this.collectionDate = collectionDate;
    }
}
