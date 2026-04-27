package com.doctour.doctourbe.dto;

public record LocationDTO (
        Long id,
        String name,
        String description,
        String city,
        String postalCode,
        String address,
        Double longitude,
        Double latitude
){}
