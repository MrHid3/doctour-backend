package com.doctour.doctourbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    final private AppUserDetailsService appUserDetailsService;
    final private EncodingService encodingService;

    @Autowired
    public CustomAuthenticationProvider(AppUserDetailsService appUserDetailsService, EncodingService encodingService){
        this.appUserDetailsService = appUserDetailsService;
        this.encodingService = encodingService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
//        String password = (String) authentication.getCredentials();

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);

        if(userDetails == null){
            throw new UsernameNotFoundException("User not found");
        }

        if(!encodingService.passwordMatches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
