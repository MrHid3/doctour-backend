package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.dto.AppUserDTO;
import com.doctour.doctourbe.exception.AppUserException;
import com.doctour.doctourbe.model.AppUser;
import com.doctour.doctourbe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private AppUserService appUserService;

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
                                () -> new AppUserException("INVALID_UUID"))
                )
        );
    }

    private AppUserDTO toDTO(AppUser appUser){
        return new AppUserDTO(
                appUser.getUuid(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getGender()
        );
    }
}
