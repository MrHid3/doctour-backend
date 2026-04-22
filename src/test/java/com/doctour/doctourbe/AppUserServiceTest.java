package com.doctour.doctourbe;

import com.doctour.doctourbe.exception.PasswordException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.repository.AppUserRepository;
import com.doctour.doctourbe.service.AppUserService;
import com.doctour.doctourbe.service.EncodingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void saveAppUser() throws PasswordException {
        AppUser user = new AppUser();
        user.setUuid(UUID.randomUUID());
        user.setUsername("bbb");
        user.setPassword("a!Aaaaaaa1a");
        Assertions.assertDoesNotThrow(() -> appUserService.save(user));
        Assertions.assertNotNull(appUserService.findByUsername("bbb"));
    }
}
