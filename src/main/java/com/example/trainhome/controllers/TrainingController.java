package com.example.trainhome.controllers;

import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.dto.TrainingDTO;
import com.example.trainhome.services.TrainingService;
import com.example.trainhome.validators.TrainingRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/trains")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @CrossOrigin
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createTraining(@RequestBody TrainingDTO trainingDTO) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        TrainingRequestValidator.validateCreateRequest(trainingDTO);
        Long id = trainingService.addTraining(trainingDTO);
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteTraining(@PathVariable("id") Long id) throws WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        trainingService.deleteTraining(id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateTraining(@RequestBody TrainingDTO trainingDTO) throws WrongPersonException, InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        TrainingRequestValidator.validateCreateRequest(trainingDTO);
        trainingService.updateTraining(trainingDTO);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getPersonTraining(@PathVariable("id") Long id) {
        Map<Object, Object> model = new HashMap<>();
        List<TrainingDTO> list = trainingService.getAllTrainings(id);
        model.put("trainings", list);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
