package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.TokenException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.VerificationToken;
import com.doctour.doctourbe.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService {

    @Autowired
    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${app.magic-link.expiry-hours}")
    private int expiryHours;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Transactional
    public VerificationToken createToken(AppUser user, VerificationToken.TokenType type){

        verificationTokenRepository.deleteByAppUserAndType(user, type);

        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setAppUser(user);
        token.setType(type);
        token.setExpiryDate(LocalDateTime.now().plusHours(expiryHours));
        return verificationTokenRepository.save(token);
    }

    public VerificationToken validateToken(String raw, VerificationToken.TokenType expectedType){
        VerificationToken token = verificationTokenRepository.findByTokenAndType(raw, expectedType)
                .orElseThrow(() -> new TokenException("TOKEN_NOT_FOUND"));

        if (token.getUsed()){
            throw new TokenException("TOKEN_ALREADY_USED");
        }
        if(token.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new TokenException("TOKEN_EXPIRED");
        }
        return token;
    }

    public void consumeToken(VerificationToken token){
        token.setUsed(true);
        verificationTokenRepository.save(token);
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void purgeExpiredToken() {
        verificationTokenRepository.deleteByExpiryDateBeforeAndUsedFalse(LocalDateTime.now());
    }
}
