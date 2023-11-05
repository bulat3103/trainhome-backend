package com.example.trainhome.controllers;

import com.example.trainhome.dto.CoachSearchDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.services.CoachService;
import com.example.trainhome.validators.CoachRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @CrossOrigin
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getFilteredCoaches (@RequestBody CoachSearchDTO coachSearchDTO) throws InvalidDataException {
        CoachRequestValidator.validateCoachDto(coachSearchDTO);
        Map<Object, Object> model = new HashMap<>();
        model.put("coaches", coachService.getFilteredCoaches(coachSearchDTO));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
