package com.doctour.doctourbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID uuid;

    @ManyToOne
    @JsonBackReference
    private AppUser appUser;

    private LocalTime start;

    private LocalTime end;

    private DayOfWeek dayOfWeek;

    @OneToOne
    private Location location;
}
