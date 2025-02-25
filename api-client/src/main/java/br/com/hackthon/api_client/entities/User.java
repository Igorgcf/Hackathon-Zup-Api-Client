package br.com.hackthon.api_client.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50 )
    private String username;

    @Column(nullable = false, unique = true, length = 50 )
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 257 )
    private String password;

    @Column(nullable = false, length = 150 )
    private String fullName;

    @Column(length = 20 )
    private String phoneNumber;

    @Column(length = 20, unique = true )
    private String cpf;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime collectionDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public User(){
    }

    public User(UUID id, String username, String email, String password, String fullName, String phoneNumber, String cpf, String imageUrl,
                LocalDateTime collectionDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.imageUrl = imageUrl;
        this.collectionDate = collectionDate;
        this.lastUpdateDate = lastUpdateDate;
    }
}
