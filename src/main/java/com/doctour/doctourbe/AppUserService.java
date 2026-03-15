package com.doctour.doctourbe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {

    final private AppUserRepository appUserRepository;
    final private EncodingService encodingService;

    @Autowired
    AppUserService(AppUserRepository appUserRepository, EncodingService encodingService){
        this.appUserRepository = appUserRepository;
        this.encodingService = encodingService;
    }

    public Optional<AppUser> findByUsername(String username) {
        return this.appUserRepository.findByUsername(username);
    }

    public Optional<AppUser> findByUuid(UUID uuid) {
        return this.appUserRepository.findByUuid(uuid);
    }

    public AppUser saveAppUser(AppUser appUser){
        appUser.setPassword(this.encodingService.encodePassword(appUser.getPassword()));
        return this.appUserRepository.save(appUser);
    }
}
