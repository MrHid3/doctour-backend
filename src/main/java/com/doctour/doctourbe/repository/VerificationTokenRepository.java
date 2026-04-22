package com.doctour.doctourbe.repository;

import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {

    Optional<VerificationToken> findByTokenAndType(String token, VerificationToken.TokenType type);

    void deleteByTokenAndType(String token, VerificationToken.TokenType type);

    void deleteByAppUserAndType(AppUser appUser, VerificationToken.TokenType type);

    void deleteByExpiryDateBeforeAndUsedFalse(LocalDateTime now);
}
