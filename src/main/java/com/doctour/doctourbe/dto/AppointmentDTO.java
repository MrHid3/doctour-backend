package com.doctour.doctourbe.dto;

import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Location;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentDTO (
    UUID uuid,
    AppUserDTO customer,
    AppUserDTO doctor,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime,
    LocationDTO location
){}
