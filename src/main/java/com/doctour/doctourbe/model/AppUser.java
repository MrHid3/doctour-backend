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

    public AppUser(String username, String password) throws PasswordException {
        this.username = username;
        this.setPassword(password);
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
    private UUID uuid;

    @Column(unique = true)
    private String username;
    private String password;

    private String email;

    @ManyToMany
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;
}