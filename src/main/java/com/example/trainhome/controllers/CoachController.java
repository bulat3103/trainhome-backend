package com.example.trainhome.controllers;

import com.example.trainhome.dto.CoachSearchDTO;
import com.example.trainhome.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @CrossOrigin
    @GetMapping(value = "list", produces = "application/json")
    public ResponseEntity<?> getFilteredCoaches (@RequestBody CoachSearchDTO coachSearchDTO) {
        Map<Object, Object> model = new HashMap<>();
        model.put("coaches", coachService.getFilteredCoaches(coachSearchDTO));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
