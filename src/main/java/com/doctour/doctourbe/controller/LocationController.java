package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.exception.LocationException;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.service.LocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> saveLocation(@RequestBody CreateLocationRequest req) {
        locationService.createLocation(req.name, req.description, req.latitude, req.longitude, req.city, req.address, req.postalCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable Long id){
        return ResponseEntity.ok(locationService.findById(id).orElseThrow(() -> new LocationException("INVALID")));
    }

    public record CreateLocationRequest(
            @NotNull String name,
            @NotNull String description,
            @NotNull BigDecimal latitude,
            @NotNull BigDecimal longitude,
            String city,
            String address,
            String postalCode
    ) {
    }
}
