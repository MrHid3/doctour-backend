package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.EmailException;
import com.doctour.doctourbe.exception.UuidException;
import com.doctour.doctourbe.repository.AppUserRepository;
import com.doctour.doctourbe.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public Optional<AppUser> findByUsername(String username) throws UsernameNotFoundException {
        return this.appUserRepository.findByUsername(username);
    }

    public Optional<AppUser> findByEmail(String email) {
        return this.appUserRepository.findByEmail(email);
    }

    public Optional<AppUser> findByUuid(UUID uuid) {
        return this.appUserRepository.findByUuid(uuid);
    }

    public AppUser createPending(String email, String username, String password) {
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setStatus(AppUser.AppUserStatus.PENDING);
        this.save(appUser);
        return appUser;
    }

    public void activate(AppUser appUser) {
        appUser.setStatus(AppUser.AppUserStatus.ACTIVE);
        this.save(appUser);
    }

    public void changePassword(AppUser appUser, String password){
        appUser.setPassword(password);
        this.save(appUser);
    }

    public AppUser save(AppUser appUser) {
        if(appUserRepository.findByEmail(appUser.getUsername()).isPresent()){
            throw new EmailException("TAKEN");
        }
        return this.appUserRepository.save(appUser);
    }
}
