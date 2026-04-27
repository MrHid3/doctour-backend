package com.doctour.doctourbe.dto;

import com.doctour.doctourbe.model.AppUser;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record AvailabilityDTO(
        UUID uuid,
        LocalTime startTime,
        LocalTime endTime,
        DayOfWeek dayOfWeek,
        LocationDTO locaiton
        ) {
}
