package br.com.hackthon.api_client.entities;

import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

public class PersonTests {

    @Test
    public void personShouldHaveCorrectStructure(){

        Person entity = new Person();

        Family family = new Family();

        UUID id = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();

        entity.setId(id);
        entity.setFirstName("Patrícia");
        entity.setLastName("Silva de Lima");
        entity.setAge(18);
        entity.setDateBirth(LocalDate.of(1994,1,1));
        entity.setCpf("123.456.789-0");
        entity.setIncome(7000.00);
        entity.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setFamily(family);

        family.setId(uuid);
        family.setLastName("Lima");
        family.setState("SP");
        family.setCity("São Paulo");
        family.setStreet("Soares de Faria");
        family.setAddressNumber(70);
        family.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));

        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getFirstName());
        Assertions.assertNotNull(entity.getLastName());
        Assertions.assertNotNull(entity.getAge());
        Assertions.assertNotNull(entity.getDateBirth());
        Assertions.assertNotNull(entity.getCpf());
        Assertions.assertNotNull(entity.getIncome());
        Assertions.assertNotNull(entity.getCollectionDate());
        Assertions.assertNotNull(entity.getFamily());
        Assertions.assertEquals(entity.getFamily().getLastName(), family.getLastName());
        Assertions.assertSame(entity.getId(), id);
        Assertions.assertSame(family.getId(), uuid);

        System.out.println("Person id: " + entity.getId());
        System.out.println("Family id: " + family.getId());

    }
}
