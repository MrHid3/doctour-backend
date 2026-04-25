package com.doctour.doctourbe.component;

import com.doctour.doctourbe.exception.PasswordException;
import com.doctour.doctourbe.exception.PrivilegeException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Gender;
import com.doctour.doctourbe.model.Privilege;
import com.doctour.doctourbe.model.Role;
import com.doctour.doctourbe.repository.AppUserRepository;
import com.doctour.doctourbe.repository.GenderRepository;
import com.doctour.doctourbe.repository.PrivilegeRepository;
import com.doctour.doctourbe.repository.RoleRepository;
import com.doctour.doctourbe.service.EncodingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private EncodingService encodingService;
    @Autowired
    private GenderRepository genderRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup){
            return;
        }

//        Privilege readAllPrivilege = createPrivilageIfNotFound("READ_ALL_MEMBERS_PRIVILAGE");
//        Privilege readOwnMembers = createPrivilageIfNotFound("READ_OWN_MEMBERS_PRIVILAGE");
//        Privilege addMembers = createPrivilageIfNotFound("CREATE_MEMBERS");
        Privilege setSchedule = createPrivilegeIfNotFound("SET_SCHEDULE");
        Privilege makeAppointment = createPrivilegeIfNotFound("MAKE_APPOINTMENT");

        Role doctor = createRoleIfNotFound("ROLE_DOCTOR", Arrays.asList(setSchedule));
        Role customer = createRoleIfNotFound("ROLE_CUSTOMER", Arrays.asList(makeAppointment));

        Gender male = createGenderIfNotFound("Male", "N");
        Gender female = createGenderIfNotFound("Female", "F");
        createGenderIfNotFound("Other", "O");

        try {
            createUserIfNotFound("bob@gmail.com", "Bob Smith", male, AppUser.AppUserStatus.ACTIVE, "153753aB!", customer);
            createUserIfNotFound("alice@gmail.com", "Alice Tung", female, AppUser.AppUserStatus.ACTIVE, "153753aB!", doctor);
        } catch (PasswordException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Optional<Privilege> optionalPrivilege = privilegeRepository.findByName(name);
        Privilege privilege;
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }else{
            privilege = optionalPrivilege.get();
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role;
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isEmpty()) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }else{
            role = optionalRole.get();
        }
        return role;
    }

    @Transactional
    Gender createGenderIfNotFound(String name, String shortname) {

        Gender gender;
        Optional<Gender> optionalGender = genderRepository.findByName(name);
        Optional<Gender> optionalGenderShort = genderRepository.findByShortname(shortname);
        if(optionalGender.isEmpty() && optionalGenderShort.isEmpty()){
            gender = new Gender();
            gender.setName(name);
            gender.setShortname(shortname);
            genderRepository.save(gender);
        }else if(optionalGender.isPresent()){
            gender = optionalGender.get();
        }else{
            gender = optionalGenderShort.get();
        }

        return gender;
    }

    @Transactional
    AppUser createUserIfNotFound(String email, String username, Gender gender, AppUser.AppUserStatus status, String password, Role role) throws PasswordException {

        AppUser appUser;
        Optional<AppUser> optionalUser = appUserRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            appUser = new AppUser();
            appUser.setUsername(username);
            appUser.setPassword(password, encodingService);
            appUser.setGender(gender);
            appUser.setRoles(List.of(role));
            appUser.setStatus(status);
        }else{
            appUser = optionalUser.get();
        }

        return appUser;
    }
}
