package com.doctour.doctourbe.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncodingService{
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encodePassword(String password) {
        return this.passwordEncoder().encode(password);
    }

    public boolean passwordMatches(String password, String hash){
        return this.passwordEncoder().matches(password, hash);
    }
}
