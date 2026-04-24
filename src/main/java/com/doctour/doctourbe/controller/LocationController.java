package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.service.LocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> saveLocation(@RequestBody CreateLocationRequest req) {
        locationService.createLocation(req.name, req.description, req.latitude, req.longitude);
        return ResponseEntity.ok().build();
    }


    public record CreateLocationRequest(
            @NotNull String name,
            @NotNull String description,
            @NotNull BigDecimal latitude,
            @NotNull BigDecimal longitude
    ) {
    }
}
