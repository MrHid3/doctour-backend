package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.dto.AppUserDTO;
import com.doctour.doctourbe.dto.AppointmentDTO;
import com.doctour.doctourbe.dto.AvailabilityDTO;
import com.doctour.doctourbe.dto.LocationDTO;
import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.exception.AppointmentException;
import com.doctour.doctourbe.exception.LocationException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Appointment;
import com.doctour.doctourbe.model.Availability;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.service.AppUserService;
import com.doctour.doctourbe.service.AppointmentService;
import com.doctour.doctourbe.service.LocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppUserService appUserService;
    private final LocationService locationService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AppUserService appUserService, LocationService locationService) {
        this.appUserService = appUserService;
        this.appointmentService = appointmentService;
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(appointmentService.
                getAll(appUserService.findByEmail(auth.getName()).get()).stream().map(this::toDTO));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getSelected(@PathVariable String uuid){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(
                toDTO(
                        appointmentService.findByUuid(UUID.fromString(uuid)).
                                orElseThrow(() -> new AppointmentException("NOT_FOUND"))
                ));
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> create(@RequestBody CreateAppointmentRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        appointmentService.create(
                appUserService.findByEmail(auth.getName()).orElseThrow(() -> new AppUserException("NOT_FOUND")),
                appUserService.findDoctor(UUID.fromString(req.doctorUuid)).orElseThrow(() -> new AppUserException("DOCTOR_NOT_FOUND")),
                LocalDate.parse(req.date),
                LocalTime.parse(req.start),
                LocalTime.parse(req.end),
                locationService.findById(req.locationId).orElseThrow(() -> new LocationException("NOT_FOUND"))
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable String uuid, @RequestBody UpdateAppointmentRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        appointmentService.update(
                appUserService.findByEmail(auth.getName()).get(),
                appointmentService.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new AppointmentException("NOT_EXIST")),
                LocalDate.parse(req.date),
                LocalTime.parse(req.start),
                LocalTime.parse(req.end)
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        appointmentService.delete(
                appUserService.findByEmail(auth.getName()).get(),
                appointmentService.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new AppointmentException("NOT_EXIST"))
        );

        return ResponseEntity.ok().build();
    }

    public AppointmentDTO toDTO(Appointment ap) {
        return new AppointmentDTO(
                ap.getUuid(),
                toDTO(ap.getCustomer()),
                toDTO(ap.getDoctor()),
                ap.getDate(),
                ap.getStartTime(),
                ap.getEndTime(),
                toDTO(ap.getLocation())
                );
    }

    public AppUserDTO toDTO(AppUser appUser) {
        return new AppUserDTO(
                appUser.getUuid(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getGender(),
                appUser.getAvailabilities().stream().map(this::toDTO).toList(),
                appUser.getSpecializations()
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

    public record CreateAppointmentRequest(
            @NotNull String doctorUuid,
            @NotNull Long locationId,
            @NotNull String date,
            @NotNull String start,
            @NotNull String end
    ) {
    }

    public record UpdateAppointmentRequest(
            String date,
            String start,
            String end
    ){}
}