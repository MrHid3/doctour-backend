package com.doctour.doctourbe.model;

import com.doctour.doctourbe.exception.InvalidPasswordException;
import jakarta.persistence.*;
import lombok.*;

import java.lang.module.InvalidModuleDescriptorException;
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

    public AppUser(String username, String password) throws InvalidPasswordException {
        this.username = username;
        this.setPassword(password);
    }

    public void setPassword(String password) throws InvalidPasswordException {
        Pattern pattern = Pattern.compile("^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9])(?=\\S*?[?!\\\\|'\";:+=-_()*&^%$#@<>,.`~\\[\\]{}/]).{8,})\\S$");
        Matcher matcher = pattern.matcher(password);
        if(!matcher.find()){
            throw new InvalidPasswordException("Password is too easy");
        }
        this.password = password;
    }

    public void setPasswordHash(String password) {
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_uuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}