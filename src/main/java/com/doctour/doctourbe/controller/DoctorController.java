package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.dto.AppUserDTO;
import com.doctour.doctourbe.dto.AvailabilityDTO;
import com.doctour.doctourbe.dto.LocationDTO;
import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.exception.SpecializationException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.model.Availability;
import com.doctour.doctourbe.model.Location;
import com.doctour.doctourbe.service.AppUserService;
import com.doctour.doctourbe.service.SpecializationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(
                appUserService.findAllDoctors().stream().map((this::toDTO))
        );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getDoctor(@PathVariable String uuid) {
        return ResponseEntity.ok(
                toDTO(
                        appUserService.findDoctor(
                                UUID.fromString(uuid)
                        ).orElseThrow(
                                () -> new AppUserException("NOT_FOUND"))
                )
        );
    }

    @PostMapping("/specialization")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> addSpecialization(@RequestBody AddSpecializationRequest req){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        appUserService.addSpecialization(
                appUserService.findByEmail(auth.getName()).get(),
                specializationService.findById(req.specializationId).orElseThrow(() -> new SpecializationException("NOT_FOUND"))
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/specialization/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> removeSpecialization(@PathVariable Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        appUserService.removeSpecialization(
                appUserService.findByEmail(auth.getName()).get(),
                specializationService.findById(id).orElseThrow(() -> new SpecializationException("NOT_FOUND"))
        );

        return ResponseEntity.ok().build();
    }

    public AppUserDTO toDTO(AppUser appUser){
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

    public record AddSpecializationRequest(
            @NotNull Long specializationId
    ){}
}
