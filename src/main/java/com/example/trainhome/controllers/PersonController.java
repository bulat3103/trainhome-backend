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
@RequestMapping(value = "/api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @CrossOrigin
    @GetMapping(value = "/email")
    ResponseEntity<?> getPersonByEmail(@RequestParam("email") String email) {
        Map<Object, Object> model = new HashMap<>();
        Person person = personService.findByEmail(email);
        model.put("person", PersonDTO.PersonToPersonDTO(person));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    ResponseEntity<?> getPersonById(@PathVariable("id") Long id) {
        Map<Object, Object> model = new HashMap<>();
        Person person = personService.findPersonById(id);
        model.put("person", PersonDTO.PersonToPersonDTO(person));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping
    ResponseEntity<?> updatePerson(@RequestBody PersonDTO personDTO) throws NoSuchPersonException, WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        PersonDTO dto = personService.update(personDTO);
        model.put("person", dto);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/image")
    ResponseEntity<?> updatePerson(@RequestBody ImageDTO imageDTO) throws InvalidDataException, WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        personService.updateImage(imageDTO);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deletePerson(@PathVariable("id") Long id) throws NoSuchPersonException {
        Map<Object, Object> model = new HashMap<>();
        personService.delete(id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
