package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.model.Appointment;
import com.doctour.doctourbe.service.AppUserService;
import com.doctour.doctourbe.service.AppointmentService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final AppUserService appUserService;

    public AppointmentController(AppointmentService appointmentService, AppUserService appUserService) {
        this.appointmentService = appointmentService;
        this.appUserService = appUserService;
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
    public ResponseEntity<?> create(){

    }

    public record CreateAppointmentRequest(
        @NotNull String doctorUuid,
        @NotNull Long locationId,
        @NotNull String date,
        @NotNull String start,
        @NotNull String end
    ){}
}
