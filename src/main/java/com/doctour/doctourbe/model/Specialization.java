package com.doctour.doctourbe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Specialization {
    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;
}
