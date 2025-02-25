package br.com.hackthon.api_client.entities;

import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

public class ChildrenTests {

    @Test
    public void childrenShouldHaveCorrectStructure(){

        Children entity = new Children();

        Family family = new Family();

        UUID id = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();

        entity.setId(id);
        entity.setFirstName("Pedro");
        entity.setLastName("Lima Andrade");
        entity.setAge(18);
        entity.setDateBirth(LocalDate.of(2006, 2,2));
        entity.setCpf("987.654.321-1");
        entity.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setFamily(family);

        family.setId(uuid);
        family.setLastName("Andrade");
        family.setState("SP");
        family.setCity("Sumaré");
        family.setStreet("Abel Menuzzo");
        family.setAddressNumber(77);
        family.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
        family.getChildren().add(entity);

        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getFirstName());
        Assertions.assertNotNull(entity.getLastName());
        Assertions.assertNotNull(entity.getAge());
        Assertions.assertNotNull(entity.getDateBirth());
        Assertions.assertNotNull(entity.getCpf());
        Assertions.assertNotNull(entity.getCollectionDate());
        Assertions.assertNotNull(entity.getFamily());
        Assertions.assertSame(entity.getFamily().getLastName(), family.getLastName());
        Assertions.assertSame(entity.getId(), id);
        Assertions.assertSame(family.getId(), uuid);

        System.out.println("Children id: " + entity.getId());
        System.out.println("Family id: " + family.getId());


    }
}
