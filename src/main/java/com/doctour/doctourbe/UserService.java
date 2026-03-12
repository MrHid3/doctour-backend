package com.doctour.doctourbe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public String create(String username, String password) throws Error {
        User user = User.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .authorities("user")
                .build();

        userRepo.save(user);

        return "User created succesfully";
    }
}
