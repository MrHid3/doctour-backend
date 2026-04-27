package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.exception.AppointmentException;
import com.doctour.doctourbe.exception.LocationException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Appointment;
import com.doctour.doctourbe.service.AppUserService;
import com.doctour.doctourbe.service.AppointmentService;
import com.doctour.doctourbe.service.LocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentService appointmentService, AppUserService appUserService, LocationService locationService, AppointmentRepository appointmentRepository) {
        this.appointmentService = appointmentService;
        this.appUserService = appUserService;
        this.locationService = locationService;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(appointmentService.getAll(appUserService.findByEmail(auth.getName()).orElseThrow(() -> new AppUserException("INVALID"))));
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

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        appointmentService.delete(
                appUserService.findByEmail(auth.getName()).get(),
                appointmentService.findByUuid(uuid).orElseThrow(() -> new AppointmentException("NOT_EXIST"))
        );

        return ResponseEntity.ok().build();
    }

    public record CreateAppointmentRequest(
        @NotNull String doctorUuid,
        @NotNull Long locationId,
        @NotNull String date,
        @NotNull String start,
        @NotNull String end
    ){}
}