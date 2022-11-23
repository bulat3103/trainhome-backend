package com.example.trainhome.userservice.controllers;


import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.services.PersonService;
import com.example.trainhome.userservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.trainhome.userservice.dto.PersonDTO;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @CrossOrigin
    @GetMapping
    ResponseEntity<?> getPersonByEmail(@RequestBody String email) {
        PersonDTO person = personService.findByEmail(email);
        return ResponseEntity.ok(person);
    }

    @CrossOrigin
    @PostMapping("/update")
    ResponseEntity<?> updatePerson(@RequestBody PersonDTO  personDTO) {
        Map<Object, Object> model = new HashMap<>();
        try{
            PersonDTO dto = personService.update(personDTO);
            model.put("person", dto);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (NoSuchPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (WrongPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @CrossOrigin
    @DeleteMapping
    ResponseEntity<?> deletePerson(@RequestParam("id") Long id) {
        Map<Object, Object> model = new HashMap<>();
        try{
            personService.delete(id);
        } catch (NoSuchPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
