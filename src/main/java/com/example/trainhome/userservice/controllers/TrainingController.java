package com.example.trainhome.userservice.controllers;

import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.userservice.dto.TrainingDTO;
import com.example.trainhome.userservice.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "trains")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @CrossOrigin
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createTraining(@RequestBody TrainingDTO trainingDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            trainingService.validateTraining(trainingDTO);
            Long id = trainingService.addTraining(trainingDTO);
            model.put("id", id);
        } catch (InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<?> deleteTraining(@RequestParam("id") Long id) {
        Map<Object, Object> model = new HashMap<>();
        try {
            trainingService.deleteTraining(id);
        } catch (WrongPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateTraining(@RequestBody TrainingDTO trainingDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            trainingService.validateTraining(trainingDTO);
            trainingService.updateTraining(trainingDTO);
        } catch (InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (WrongPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
