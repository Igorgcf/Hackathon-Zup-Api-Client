package br.com.hackthon.api_client.entities;

import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

public class FamilyTests {

    @Test
    public void familyShouldHaveCorrectStructure(){

        Family entity = new Family();

        Person person = new Person();

        Children children = new Children();

        UUID id = UUID.randomUUID();
        UUID personUUID = UUID.randomUUID();
        UUID childrenUUID = UUID.randomUUID();

        entity.setId(id);
        entity.setLastName("Lima");
        entity.setState("SP");
        entity.setCity("Campinas");
        entity.setStreet("Barreto Leme");
        entity.setAddressNumber(170);
        entity.getMembers().add(person);
        entity.getChildren().add(children);
        entity.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));

        person.setId(personUUID);
        person.setFirstName("Lucas");
        person.setLastName("Fabrício Lima");
        person.setAge(30);
        person.setDateBirth(LocalDate.of(1994, 3,3));
        person.setIncome(7000.00);
        person.setCpf("231.645.978-3");
        person.setFamily(entity);

        children.setId(childrenUUID);
        children.setFirstName("Larissa");
        children.setLastName("Lima Albuquerque");
        children.setAge(18);
        children.setDateBirth(LocalDate.of(2006,4,4));
        children.setCpf("132.213.123-4");
        children.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
        children.setFamily(entity);

        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getLastName());
        Assertions.assertNotNull(entity.getState());
        Assertions.assertNotNull(entity.getCity());
        Assertions.assertNotNull(entity.getStreet());
        Assertions.assertNotNull(entity.getAddressNumber());
        Assertions.assertNotNull(entity.getMembers());
        Assertions.assertNotNull(entity.getChildren());
        Assertions.assertNotNull(entity.getCollectionDate());

        Assertions.assertSame(entity.getId(), id);
        Assertions.assertSame(person.getId(), personUUID);
        Assertions.assertSame(children.getId(), childrenUUID);

        System.out.println("Family id: " + entity.getId());
        System.out.println("Person id: " + personUUID);
        System.out.println("Children id: " + childrenUUID);

    }
}
