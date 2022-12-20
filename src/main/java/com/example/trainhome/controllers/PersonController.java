package com.example.trainhome.controllers;


import com.example.trainhome.dto.ImageDTO;
import com.example.trainhome.entities.Person;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.trainhome.dto.PersonDTO;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @CrossOrigin
    @GetMapping
    ResponseEntity<?> getPersonByEmail(@RequestParam String email) {
        Map<Object, Object> model = new HashMap<>();
        Person person = personService.findByEmail(email);
        model.put("person", PersonDTO.PersonToPersonDTO(person));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/id")
    ResponseEntity<?> getPersonById(@RequestParam Long id) {
        Map<Object, Object> model = new HashMap<>();
        Person person = personService.findPersonById(id);
        model.put("person", PersonDTO.PersonToPersonDTO(person));
        return new ResponseEntity<>(model, HttpStatus.OK);
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
            model.put("person", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (WrongPersonException e) {
            model.put("person", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @CrossOrigin
    @PostMapping("/image")
    ResponseEntity<?> updatePerson(@RequestBody ImageDTO imageDTO) {
        Map<Object, Object> model = new HashMap<>();
        try{
            personService.updateImage(imageDTO);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (WrongPersonException | InvalidDataException e) {
            model.put("person", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping
    ResponseEntity<?> deletePerson(@RequestParam("id") Long id) {
        Map<Object, Object> model = new HashMap<>();
        try{
            personService.delete(id);
        } catch (NoSuchPersonException e) {
            model.put("person", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
