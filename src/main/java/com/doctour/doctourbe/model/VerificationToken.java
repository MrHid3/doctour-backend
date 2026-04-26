package com.doctour.doctourbe.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class VerificationToken {

    public enum TokenType{
        REGISTRATION,
        PASSWORD_RESET
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String uuid;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    @Column
    private LocalDateTime expiryDate;

    private Boolean used = false;
}
