package com.doctour.doctourbe.dto;

import com.doctour.doctourbe.model.Gender;
import com.doctour.doctourbe.model.Location;

import java.util.UUID;

public record AppUserDTO(
    UUID uuid,
    String username,
    String email,
    Gender gender,
    Location location
    ){
}
