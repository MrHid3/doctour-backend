package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.exception.AvailabilityException;
import com.doctour.doctourbe.exception.LocationException;
import com.doctour.doctourbe.model.Availability;
import com.doctour.doctourbe.service.AppUserService;
import com.doctour.doctourbe.service.AvailabilityService;
import com.doctour.doctourbe.service.LocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final LocationService locationService;
    private final AppUserService appUserService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService, LocationService locationService, AppUserService appUserService){
        this.availabilityService = availabilityService;
        this.locationService = locationService;
        this.appUserService = appUserService;
    }

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> add(@RequestBody AddAvailabilityRequest req){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        availabilityService.create(
                appUserService.findByEmail(auth.getName()).get(),
                locationService.findById(req.locationId).orElseThrow(() -> new LocationException("INVALID")),
                DayOfWeek.of(req.dayOfWeek),
                LocalTime.parse(req.start),
                LocalTime.parse(req.end)
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@RequestBody UpdateAvailabilityRequest req, @PathVariable String uuid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Availability av = availabilityService.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new AvailabilityException("NOT_EXIST"));

        if (av.getAppUser() != appUserService.findByEmail(auth.getName()).orElseThrow(() -> new AppUserException("INVALID"))) {
            throw new AvailabilityException("NO_ACCESS");
        }

        availabilityService.update(
                av,
                locationService.findById(req.locationId).orElseThrow(() -> new LocationException("NOT_EXIST")),
                LocalTime.parse(req.start),
                LocalTime.parse(req.end)
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Availability av = availabilityService.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new AvailabilityException("NOT_EXIST"));

        if (av.getAppUser() != appUserService.findByEmail(auth.getName()).get()) {
            throw new AvailabilityException("NO_ACCESS");
        }

        availabilityService.delete(av);

        return ResponseEntity.ok().build();
    }

    public record AddAvailabilityRequest(
            @NotNull Long locationId,
            @NotNull String start,
            @NotNull String end,
            @NotNull Integer dayOfWeek
    ) {}

    public record UpdateAvailabilityRequest(
            Long locationId,
            String start,
            String end
    ) {}
}
