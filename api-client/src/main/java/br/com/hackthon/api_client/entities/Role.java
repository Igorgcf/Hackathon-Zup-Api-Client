package br.com.hackthon.api_client.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "tb_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 70, unique = true)
    private String authority;

    public Role(){
    }

    public Role(UUID id, String authority) {
        this.id = id;
        this.authority = authority;
    }
}
