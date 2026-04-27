package com.doctour.doctourbe.repository;

import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByUsername(String username) throws UsernameNotFoundException;

    Optional<AppUser> findByUuid(UUID uuid);

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByUuidAndRolesContains(UUID uuid, Role role);

    List<AppUser> findByRolesContains(Role role);
}
