package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.exception.EmailException;
import com.doctour.doctourbe.model.Privilege;
import com.doctour.doctourbe.model.Role;
import com.doctour.doctourbe.model.AppUser;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    final private AppUserService appUserService;

    @Autowired
    public AppUserDetailsService(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<AppUser> optionalAppUser = appUserService.findByEmail(username);
        if (optionalAppUser.isEmpty()) {
            throw new EmailException("NOT_FOUND");
        }
        AppUser appUser = optionalAppUser.get();
        return new User(appUser.getUsername(), appUser.getPassword(), getGrantedAuthorities(appUser.getRoles()));
    }

    public UserDetails loadUserByUuid(UUID uuid) throws AppUserException {
        Optional<AppUser> optionalAppUser = appUserService.findByUuid(uuid);
        if (optionalAppUser.isEmpty()) {
            throw new AppUserException("NOT_FOUND");
        }
        AppUser appUser = optionalAppUser.get();
        return new User(appUser.getUsername(), appUser.getPassword(), getGrantedAuthorities(appUser.getRoles()));
    }

    private Collection<GrantedAuthority> getAuthoritiesList(String role){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
