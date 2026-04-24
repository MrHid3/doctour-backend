package com.doctour.doctourbe.service;

import com.doctour.doctourbe.dto.AppUserDTO;
import com.doctour.doctourbe.exception.EmailException;
import com.doctour.doctourbe.model.Gender;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.model.Role;
import com.doctour.doctourbe.repository.AppUserRepository;
import com.doctour.doctourbe.model.AppUser;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EncodingService encodingService;

    @Autowired
    private RoleService roleService;

    public Optional<AppUser> findByUsername(String username) throws UsernameNotFoundException {
        return this.appUserRepository.findByUsername(username);
    }

    public Optional<AppUser> findByEmail(String email) {
        return this.appUserRepository.findByEmail(email);
    }

    public Optional<AppUser> findByUuid(UUID uuid) {
        return this.appUserRepository.findByUuid(uuid);
    }

        public AppUser createPending(String email, String username, String password, Role role, Gender gender) {
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setStatus(AppUser.AppUserStatus.PENDING);
        appUser.setRoles(List.of(role));
        appUser.setGender(gender);
        this.save(appUser);
        return appUser;
    }

    public AppUser createPending(String email, String username, String password, Role role, Gender gender, Location location) {
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setStatus(AppUser.AppUserStatus.PENDING);
        appUser.setRoles(List.of(role));
        appUser.setGender(gender);
        appUser.setLocation(location);
        this.save(appUser);
        return appUser;
    }

    public Optional<AppUser> findDoctor(UUID uuid) {
        Role doctor = roleService.findByName("ROLE_DOCTOR").orElseThrow(() -> new InternalException(""));
        return this.appUserRepository.findByUuidAndRolesIs(uuid,  List.of(doctor));
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
