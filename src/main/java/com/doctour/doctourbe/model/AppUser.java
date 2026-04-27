package com.doctour.doctourbe.model;

import com.doctour.doctourbe.exception.PasswordException;
import com.doctour.doctourbe.service.EncodingService;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Entity
@Getter
@Setter
@ToString
@Table(name = "\"user\"")
public class AppUser {

    public AppUser(){};

    public AppUser(String username, String password, EncodingService encodingService) throws PasswordException {
        this.username = username;
        this.setPassword(password, encodingService);
    }

    public void setPassword(String password, EncodingService encodingService) throws PasswordException {
        Pattern pattern = Pattern.compile("^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9])(?=\\S*?[?!\\\\|'\";:+=-_()*&^%$#@<>,.`~\\[\\]{}/]).{8,})\\S$");
        Matcher matcher = pattern.matcher(password);
        if(!matcher.find()){
            throw new PasswordException("TOO_EASY");
        }
        this.password = encodingService.encodePassword(password);
    }

    public enum AppUserStatus {
        PENDING, ACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String username;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_uuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppUserStatus status = AppUserStatus.PENDING;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Gender gender;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Availability> availabilities;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Specialization> specializations;
}