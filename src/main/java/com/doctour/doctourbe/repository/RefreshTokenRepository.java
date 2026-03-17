package com.doctour.doctourbe.repository;

import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByAppUser(AppUser appUser);

}
