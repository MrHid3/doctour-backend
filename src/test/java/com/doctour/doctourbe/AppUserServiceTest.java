package com.doctour.doctourbe;

import org.checkerframework.checker.mustcall.qual.MustCall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AppUserServiceTest.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private EncodingService encodingService;

    @InjectMocks
    private AppUserService appUserService;

    @Test
    void saveAppUser(){
        AppUser user = new AppUser();
        user.setUuid(UUID.randomUUID());
        user.setUsername("bbb");
        user.setPassword("aaaaa");
        appUserService.saveAppUser(user);
        Assertions.assertNotNull(appUserService.findByUsername("bbb"));
    }
}
