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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
public class EatCalendarController {

    @Autowired
    private EatCalendarService eatCalendarService;

    @CrossOrigin
    @GetMapping(value = "list/dates", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getPersonsDates() {
        Map<Object, Object> model = new HashMap<>();
        model.put("dates", eatCalendarService.getPersonDates());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "list", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getPersonsRecommendationByDate(@RequestParam("date") String dateS) {
        Map<Object, Object> model = new HashMap<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd.MM.yyyy");
            List<EatCalendarDTO> list = eatCalendarService.getPersonRecommendationByDate(new Date(format.parse(dateS).getTime()));
            model.put("recommendations", list);
        } catch (ParseException e) {
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
