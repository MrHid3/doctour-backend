package com.doctour.doctourbe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<User, Integer> {
    UserDetails findByUsername(String username);
}
