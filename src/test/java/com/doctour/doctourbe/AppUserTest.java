package com.doctour.doctourbe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = AppUser.class)
public class AppUserTest {
    @Test
    void createAppUser(){
        AppUser user = new AppUser();
        user.setUuid(UUID.randomUUID());
        user.setUsername("bbb");
        user.setPassword("aaaaa");
        Assertions.assertNotNull(user);
    }
}
