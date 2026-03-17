package com.doctour.doctourbe.service;

import com.doctour.doctourbe.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AppUserService appUserService;

    public Optional<User> loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalAppUser = this.appUserService.findByUsername(username);
        if(optionalAppUser.isEmpty()){
            return Optional.empty();
        }
        AppUser appUser = optionalAppUser.get();
        return Optional.of(new User(appUser.getUsername(), appUser.getPassword(), getAuthorities("USER")));
    }

    private Collection<GrantedAuthority> getAuthorities(String role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
