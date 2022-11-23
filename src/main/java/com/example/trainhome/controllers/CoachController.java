package com.example.trainhome.controllers;

import com.example.trainhome.dto.CoachSearchDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "coach")
public class CoachController {

    @CrossOrigin
    @GetMapping(value = "list", produces = "application/json")
    public ResponseEntity<?> getFilteredCoaches (@RequestBody CoachSearchDTO coachSearchDTO) {
        return null;
    }
}
