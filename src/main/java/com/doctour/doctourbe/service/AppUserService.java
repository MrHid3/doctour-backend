package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.EmailException;
import com.doctour.doctourbe.exception.PasswordException;
import com.doctour.doctourbe.model.*;
import com.doctour.doctourbe.repository.AppUserRepository;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public List<AppUser> findAllDoctors(){
        return this.appUserRepository.findByRolesContains(roleService.findByName("ROLE_DOCTOR").orElseThrow(() -> new InternalException("ROLE_MISSING")));
    }

    public Optional<AppUser> findByUuid(UUID uuid) {
        return this.appUserRepository.findByUuid(uuid);
    }

        public AppUser createPending(String email, String username, String password, Role role, Gender gender) throws PasswordException {
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setUsername(username);
        appUser.setPassword(password, encodingService);
        appUser.setStatus(AppUser.AppUserStatus.PENDING);
        appUser.setRoles(List.of(role));
        appUser.setGender(gender);
        this.save(appUser);
        return appUser;
    }

    public AppUser createPending(String email, String username, String password, Role role, Gender gender, Location location) throws PasswordException {
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setUsername(username);
        appUser.setPassword(password, encodingService);
        appUser.setStatus(AppUser.AppUserStatus.PENDING);
        appUser.setRoles(List.of(role));
        appUser.setGender(gender);
        this.save(appUser);
        return appUser;
    }

    public Optional<AppUser> findDoctor(UUID uuid) {
        Role doctor = roleService.findByName("ROLE_DOCTOR").get();
        return this.appUserRepository.findByUuidAndRolesContains(uuid,  doctor);
    }

    public void activate(AppUser appUser) {
        appUser.setStatus(AppUser.AppUserStatus.ACTIVE);
        this.save(appUser);
    }

    public void changePassword(AppUser appUser, String password) throws PasswordException {
        appUser.setPassword(password, encodingService);
        this.save(appUser);
    }

    public void addSpecialization(AppUser appUser, Specialization specialization){
       Collection<Specialization> appUserSpecs = appUser.getSpecializations();
       appUserSpecs.add(specialization);
       appUser.setSpecializations(appUserSpecs);
       this.save(appUser);
    }

    public void removeSpecialization(AppUser appUser, Specialization specialization){
        Collection<Specialization> appUserSpecs = appUser.getSpecializations();
        appUserSpecs.remove(specialization);
        appUser.setSpecializations(appUserSpecs);
        this.save(appUser);
    }

    public void addAvailability(AppUser appUser, Availability availability){
        Collection<Availability> appUserAvals = appUser.getAvailabilities();
        appUserAvals.add(availability);
        appUser.setAvailabilities(appUserAvals);
        this.save(appUser);
    }

    public void removeAvailability(AppUser appUser, Availability availability){
        Collection<Availability> appUserAvals = appUser.getAvailabilities();
        appUserAvals.remove(availability);
        appUser.setAvailabilities(appUserAvals);
        this.save(appUser);
    }

    public AppUser save(AppUser appUser) {
        if(appUserRepository.findByEmail(appUser.getUsername()).isPresent()){
            throw new EmailException("TAKEN");
        }
        return this.appUserRepository.save(appUser);
    }

}
