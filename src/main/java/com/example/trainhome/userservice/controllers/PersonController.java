package com.example.trainhome.userservice.controllers;


import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.services.PersonService;
import com.example.trainhome.userservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.trainhome.userservice.dto.PersonDTO;


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
        try{
            return ResponseEntity.ok(personService.update(personDTO));
        } catch (NoSuchPersonException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping
    ResponseEntity<?> deletePerson(@RequestBody PersonDTO  personDTO) {
        try{
            return ResponseEntity.ok(personService.delete(personDTO));
        } catch (NoSuchPersonException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
