package com.doctour.doctourbe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    final private AppUserService appUserService;

    @Autowired
    public AppUserDetailsService(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalAppUser = appUserService.findByUsername(username);
        if (optionalAppUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        AppUser appUser = optionalAppUser.get();
        return new User(appUser.getUsername(), appUser.getPassword(), getAuthoritiesList("USER"));
    }

    public UserDetails loadUserByUuid(UUID uuid) throws UsernameNotFoundException{
        Optional<AppUser> optionalAppUser = appUserService.findByUuid(uuid);
        if (optionalAppUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        AppUser appUser = optionalAppUser.get();
        return new User(appUser.getUsername(), appUser.getPassword(), getAuthoritiesList("USER"));
    }

    private List<GrantedAuthority> getAuthoritiesList(String role){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
