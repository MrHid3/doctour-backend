package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.exception.SpecializationException;
import com.doctour.doctourbe.model.Specialization;
import com.doctour.doctourbe.service.SpecializationService;
import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<List<Specialization>> getAll(){
        return ResponseEntity.ok(specializationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getSpecialization(@PathVariable Long id){
        return ResponseEntity.ok(specializationService.findById(id).orElseThrow(() -> new SpecializationException("INVALID")));
    }

    @PostMapping()
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> add(@RequestBody CreateSpecializationRequest req){
        specializationService.create(req.name, req.description);
        return ResponseEntity.ok().build();
    }

    public record CreateSpecializationRequest(
            @NotNull String name,
            String description
    ){}
}
