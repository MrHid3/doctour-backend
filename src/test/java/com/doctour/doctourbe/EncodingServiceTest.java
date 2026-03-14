package com.doctour.doctourbe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = EncodingService.class)
public class EncodingServiceTest {
    @Autowired
    private EncodingService encodingService;

    @Test
    void encodedPassword() {
        String password = "abhadbhdbgu";
        String encodedPassword = this.encodingService.encodePassword(password);
        Assertions.assertNotEquals(password, encodedPassword);
    }

    @Test
    void matchPasswords() {
        String password = "abhadbhdbgu";
        String encodedPassword1 = this.encodingService.encodePassword(password);
        String encodedPassword2 = this.encodingService.encodePassword(password);
        Assertions.assertNotEquals(encodedPassword1, encodedPassword2);
        Assertions.assertTrue(encodingService.passwordMatches(password, encodedPassword2));
    }
}
