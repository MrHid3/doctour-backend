package com.doctour.doctourbe.dto;

import com.doctour.doctourbe.model.Availability;
import com.doctour.doctourbe.model.Gender;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.model.Specialization;

import java.util.Collection;
import java.util.UUID;

public record AppUserDTO(
    UUID uuid,
    String username,
    String email,
    Gender gender,
    Collection<AvailabilityDTO> availabilities,
    Collection<Specialization> specializations
    ){
}
