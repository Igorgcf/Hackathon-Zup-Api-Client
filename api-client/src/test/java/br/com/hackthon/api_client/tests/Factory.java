package br.com.hackthon.api_client.tests;

import br.com.hackthon.api_client.dto.*;
import br.com.hackthon.api_client.entities.*;
import br.com.hackthon.api_client.repositories.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;
import java.util.*;

public class Factory {

    @Autowired
    private static FamilyRepository repository;

    public static Person createdPerson() {

        UUID id = UUID.randomUUID();

        Person entity = new Person(id, "Kaio", "Luiz Felipe", LocalDate.of(1999, 1, 1), 25, "323.244.300-30", 70000.00, LocalDateTime.now());

        return entity;
    }

    public static PersonDTO createdPersonDtoToInsert() {

        UUID id = UUID.randomUUID();

        PersonDTO dto = new PersonDTO(id, "Luana", "Fernanda Souza", LocalDate.of(1998, 1, 1), 26, "123.244.300-30", 60000.00, LocalDateTime.now());

        return dto;
    }

    public static PersonDTO createdPersonDtoUpdate() {

        UUID id = UUID.randomUUID();

        PersonDTO dto = new PersonDTO(id, "Kaio", "Luiz Felipe", LocalDate.of(1999, 1, 1), 25, "323.244.300-30", 70000.00, LocalDateTime.now());

        return dto;
    }

    public static Family createdFamily() {

        UUID id = UUID.randomUUID();

        Family entity = new Family(id, "Braga Luiz", "SP", "São Paulo", "Soares de Lima", 77, LocalDateTime.now());
        return entity;
    }

    public static FamilyDTO createdFamilyDtoToInsert(){

        UUID id = UUID.randomUUID();

        FamilyDTO dto = new FamilyDTO(id, "Braga Luiz", "SP", "São Paulo", "Soares de Lima", 77, LocalDateTime.now(), LocalDateTime.now());

        return dto;

    }

    public static FamilyDTO createdFamilyDtoToUpdate(){

        UUID id = UUID.randomUUID();

        FamilyDTO dto = new FamilyDTO(id, "Fernando Luiz", "SP", "São Paulo", "Soares de Souza", 777, LocalDateTime.now(), LocalDateTime.now());

        return dto;

    }

    public static Children createdChildren(){

        UUID id = UUID.randomUUID();

        Children entity = new Children(id, "Lucas", "Guitierres", LocalDate.of(2005, 1 , 1), 19, "123.456.789-11", LocalDateTime.now());
        return entity;
    }

    public static ChildrenDTO createdChildrenDtoToInsert(){

        UUID id = UUID.randomUUID();

        ChildrenDTO dto = new ChildrenDTO(id, "Albert", "Souza", LocalDate.of(2005, 2,2), 19, "742.218.630-57", LocalDateTime.now());
        return dto;
    }

    public static ChildrenDTO createdChildrenDtoToUpdate(){

        UUID id = UUID.randomUUID();

        ChildrenDTO dto = new ChildrenDTO(id, "Francisco", "Ferraz", LocalDate.of(2004, 3,3), 20, "742.218.630-58", LocalDateTime.now());
        return dto;
    }

    public static Role createdRole(){

        UUID id = UUID.randomUUID();

        Role entity = new Role(id,"Seller");
        return entity;
    }

    public static RoleDTO createdRoleDtoToInsert(){

        UUID id = UUID.randomUUID();
        RoleDTO dto = new RoleDTO(id, "Assistent");

        return dto;
    }

    public static RoleDTO createdRoleDtoToUpdate(){

        UUID id = UUID.randomUUID();
        RoleDTO dto = new RoleDTO(id, "Partner");

        return dto;
    }

    public static User createdUser(){

        UUID id = UUID.randomUUID();
        User entity = new User(id, "VictorTechnology", "victor@gmail.com", "1234567", "Victor Lima" , "+55 19 98765-4322", "123.654.789-1", "www.image.com", LocalDateTime.now(), LocalDateTime.now());

        return entity;
    }

    public static UserDTO createdUserDtoToInsert(){

        UUID id = UUID.randomUUID();

        UserDTO dto = new UserDTO(id, "LucasTechnology", "lucas@gmail.com", "1234567", "Lucas Lima" , "+55 19 98765-4323", "123.654.789-2", "www.image.com");

        return dto;
    }

    public static UserDTO createdUserDtoToUpdate(){

        UUID id = UUID.randomUUID();

        UserDTO dto = new UserDTO(id, "BrunoTechnology", "bruno@gmail.com", "1234567", "Bruno Lima" , "+55 19 98765-4324", "123.654.789-3", "www.image.com");

        return dto;
    }
}

