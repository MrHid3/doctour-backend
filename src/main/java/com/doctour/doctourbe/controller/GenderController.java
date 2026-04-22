package com.doctour.doctourbe.controller;

import com.doctour.doctourbe.model.Gender;
import com.doctour.doctourbe.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/gender")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(genderService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CreateGenderRequest req){
        genderService.createGender(req.name, req.shortname);
        return ResponseEntity.ok().build();
    }

    public record CreateGenderRequest(String name, String shortname){
    }
}
