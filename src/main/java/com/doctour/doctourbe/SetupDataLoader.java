package com.doctour.doctourbe;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleNotFoundException;
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

        if (alreadySetup)
            return;
//        Privilege readAllPrivilege = createPrivilageIfNotFound("READ_ALL_MEMBERS_PRIVILAGE");
//        Privilege readOwnMembers = createPrivilageIfNotFound("READ_OWN_MEMBERS_PRIVILAGE");
//        Privilege addMembers = createPrivilageIfNotFound("CREATE_MEMBERS");
            Privilege setSchedule = createPrivilageIfNotFound("SET_SCHEDULE");
            Privilege makeAppointment = createPrivilageIfNotFound("MAKE_APPOINTMENT");

    }

    @Transactional
    Privilege createPrivilageIfNotFound(String name) {

        Privilege privilage = privilegeRepository.findByName(name);
        if (privilage == null) {
            privilage = new Privilege(name);
            privilegeRepository.save(privilage);
        }
        return privilage;
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
