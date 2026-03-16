package com.doctour.doctourbe;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
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
        createRoleIfNotFound("ROLE_DOCTOR", Arrays.asList(setSchedule));
        createRoleIfNotFound("ROLE_CUSTOMER", Arrays.asList(makeAppointment));
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
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
}
