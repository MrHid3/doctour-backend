package com.doctour.doctourbe.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "doctor_uuid")
    private AppUser doctor;

    @ManyToOne
    @JoinColumn(name = "customer_uuid")
    private AppUser customer;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private LocalDate date;

    private LocalTime start;

    private LocalTime end;
}
