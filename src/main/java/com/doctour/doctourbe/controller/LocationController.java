package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.dto.AvailabilityDTO;
import com.doctour.doctourbe.dto.LocationDTO;
import com.doctour.doctourbe.exception.LocationException;
import com.doctour.doctourbe.model.Availability;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.service.AvailabilityService;
import com.doctour.doctourbe.service.LocationService;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;
    private final AvailabilityService availabilityService;

    public LocationController(LocationService locationService, AvailabilityService availabilityService) {
        this.locationService = locationService;
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLocations() {
        return ResponseEntity.ok(locationService.findAll().stream().map(this::toDTO));
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> saveLocation(@RequestBody CreateLocationRequest req) {
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = factory.createPoint(new Coordinate(req.longitude, req.latitude));
        locationService.createLocation(req.name, req.description, point, req.city, req.address, req.postalCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(locationService.findById(id).orElseThrow(() -> new LocationException("NOT_FOUND"))));
    }

    @GetMapping("/at")
    public ResponseEntity<?> getLocationsAtDistance(@RequestParam Double radius, @RequestParam Double longitude, @RequestParam Double latitude) {
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        Point center = factory.createPoint(new Coordinate(longitude, latitude));
        return ResponseEntity.ok(locationService.findInRadius(center, radius).stream().map(this::toDTO));
    }

    public LocationDTO toDTO(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getName(),
                location.getDescription(),
                location.getCity(),
                location.getPostalCode(),
                location.getAddress(),
                location.getCoordinates().getX(),
                location.getCoordinates().getY()
        );
    }

    public  LocationAvailabilities getLocationAvailibilities(Location location) {
        return new LocationAvailabilities(
                availabilityService.findInLocation(location).stream().map(this::toDTO).toList(),
                location.getId(),
                location.getName(),
                location.getDescription(),
                location.getCity(),
                location.getPostalCode(),
                location.getAddress(),
                location.getCoordinates().getX(),
                location.getCoordinates().getY()
        );
    }

    public AvailabilityDTO toDTO(Availability av){
        return new AvailabilityDTO(
                av.getUuid(),
                av.getStartTime(),
                av.getEndTime(),
                av.getDayOfWeek(),
                toDTO(av.getLocation())
        );
    }

    public record LocationAvailabilities(
            List<AvailabilityDTO> availabilites,
            Long id,
            String name,
            String description,
            String city,
            String postalCode,
            String address,
            Double longitude,
            Double latitude
    ) {
    }

    public record CreateLocationRequest(
            @NotNull String name,
            @NotNull String description,
            @NotNull Double latitude,
            @NotNull Double longitude,
            String city,
            String address,
            String postalCode
    ) {
    }
}
