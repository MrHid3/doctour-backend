package com.doctour.doctourbe;

import com.doctour.doctourbe.exception.PasswordException;
import com.doctour.doctourbe.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = AppUser.class)
public class AppUserTest {
    @Test
    void createAppUser() throws PasswordException {
        AppUser user = new AppUser();
        user.setUuid(UUID.randomUUID());
        user.setUsername("bbb");
        user.setPassword("a!Aaaaaaaa1");
        Assertions.assertNotNull(user);
    }
}
