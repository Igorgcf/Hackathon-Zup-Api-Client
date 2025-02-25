package br.com.hackthon.api_client.entities;

import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

public class UserTests {

    @Test
    public void userShouldHaveCorrectStructure(){

        User entity = new User();

        Role role = new Role();

        UUID id = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();

        entity.setId(id);
        entity.setFullName("Bruna Lima");
        entity.setUsername("BrunaLima");
        entity.setEmail("bruna@gmail.com");
        entity.setPassword("1234567");
        entity.setCpf("987.654.321-0");
        entity.setPhoneNumber("11 99754-4321");
        entity.setImageUrl("www.img.com");
        entity.setCollectionDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.getRoles().add(role);

        role.setId(uuid);
        role.setAuthority("Boss");

        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getFullName());
        Assertions.assertNotNull(entity.getUsername());
        Assertions.assertNotNull(entity.getEmail());
        Assertions.assertNotNull(entity.getPassword());
        Assertions.assertNotNull(entity.getCpf());
        Assertions.assertNotNull(entity.getPhoneNumber());
        Assertions.assertNotNull(entity.getImageUrl());
        Assertions.assertNotNull(entity.getCollectionDate());
        Assertions.assertNotNull(entity.getRoles());

        Assertions.assertSame(entity.getId(), id);
        Assertions.assertSame(role.getId(), uuid);
        Assertions.assertEquals(entity.getRoles().get(0).getAuthority(), role.getAuthority());

        System.out.println("User id: " + entity.getId());
        System.out.println("Role id: " + role.getId());
    }
}
