package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.InvalidPasswordException;
import com.doctour.doctourbe.exception.UsernameTakenException;
import com.doctour.doctourbe.repository.AppUserRepository;
import com.doctour.doctourbe.model.AppUser;
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

    public AppUser saveAppUser(AppUser appUser) throws UsernameTakenException{
        if(appUserRepository.findByUsername(appUser.getUsername()).isPresent()){
            throw new UsernameTakenException("Username taken");
        }
        appUser.setPasswordHash(this.encodingService.encodePassword(appUser.getPassword()));
        return this.appUserRepository.save(appUser);
    }
}
