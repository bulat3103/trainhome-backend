package com.example.trainhome.controllers;

import com.example.trainhome.dto.EatCalendarDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.RecommendationNotFoundException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.services.EatCalendarService;
import com.example.trainhome.validators.EatCalendarRequestValidator;
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
@RequestMapping("/api/v1/calendar")
public class EatCalendarController {

    @Autowired
    private EatCalendarService eatCalendarService;

    @CrossOrigin
    @GetMapping(value = "/person/dates", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getPersonsDates() {
        Map<Object, Object> model = new HashMap<>();
        model.put("dates", eatCalendarService.getPersonDates());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/person/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getPersonsRecommendationByDate(@PathVariable("id") Long personId, @RequestParam("date") String dateS) throws ParseException {
        Map<Object, Object> model = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        List<EatCalendarDTO> list = eatCalendarService.getPersonRecommendationByDate(personId, new Date(format.parse(dateS).getTime()));
        model.put("recommendations", list);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/coach/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getCoachRecommendation(@PathVariable("id") Long personId, @RequestParam("date") Date date) throws RecommendationNotFoundException {
        Map<Object, Object> model = new HashMap<>();
        EatCalendarDTO toReturn = eatCalendarService.getCoachRecommendation(personId, date);
        model.put("recommendation", toReturn);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> createRecommendation(@RequestBody EatCalendarDTO eatCalendarDTO) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        EatCalendarRequestValidator.validateCreateRequest(eatCalendarDTO);
        Long id = eatCalendarService.createRecommendation(eatCalendarDTO);
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRecommendation(@PathVariable("id") Long id) throws WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        eatCalendarService.deleteRecommendation(id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateRecommendation(@RequestBody EatCalendarDTO eatCalendarDTO) throws InvalidDataException, WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        eatCalendarService.updateRecommendation(eatCalendarDTO);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
