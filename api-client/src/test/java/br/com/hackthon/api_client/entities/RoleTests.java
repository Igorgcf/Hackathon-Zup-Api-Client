package br.com.hackthon.api_client.entities;

import org.junit.jupiter.api.*;

import java.util.*;

public class RoleTests {

    @Test
    public void roleShouldHaveCorrectStructure(){

        Role entity = new Role();

        UUID id = UUID.randomUUID();

        entity.setId(id);
        entity.setAuthority("Boss");

        Assertions.assertSame(entity.getId(), id);
        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getAuthority());
        Assertions.assertSame(entity.getAuthority(), "Boss");

        System.out.println("Entity id: " + entity.getId());
    }
}
