package com.example.trainhome.controllers;

import com.example.trainhome.dto.EatCalendarDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.RecommendationNotFoundException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.services.EatCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
public class EatCalendarController {

    @Autowired
    private EatCalendarService eatCalendarService;

    @CrossOrigin
    @GetMapping(value = "list", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getPersonsRecommendation(@RequestParam("date") Date date) {
        Map<Object, Object> model = new HashMap<>();
        try {
            List<EatCalendarDTO> list = eatCalendarService.getPersonRecommendation(date);
            model.put("recommendations", list);
        } catch (RecommendationNotFoundException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getCoachRecommendation(@RequestParam("personId") Long personId, @RequestParam("date") Date date) {
        Map<Object, Object> model = new HashMap<>();
        try {
            EatCalendarDTO toReturn = eatCalendarService.getCoachRecommendation(personId, date);
            model.put("recommendation", toReturn);
        } catch (RecommendationNotFoundException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> createRecommendation(@RequestBody EatCalendarDTO eatCalendarDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            eatCalendarService.validateRecommendation(eatCalendarDTO);
            Long id = eatCalendarService.createRecommendation(eatCalendarDTO);
            model.put("id", id);
        } catch (InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<?> deleteRecommendation(@RequestParam("id") Long id) {
        Map<Object, Object> model = new HashMap<>();
        try {
            eatCalendarService.deleteRecommendation(id);
        } catch (WrongPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateRecommendation(@RequestBody EatCalendarDTO eatCalendarDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            eatCalendarService.updateRecommendation(eatCalendarDTO);
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
