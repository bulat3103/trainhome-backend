package com.example.trainhome.controllers;

import com.example.trainhome.dto.GroupsDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.services.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @CrossOrigin
    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<?> getAllGroups() {
        Map<Object, Object> model = new HashMap<>();
        model.put("groups", groupsService.getAllGroups());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getPersonsInGroup(@PathVariable Long id) {
        Map<Object, Object> model = new HashMap<>();
        model.put("persons", groupsService.getPersonsInGroup(id));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> addPersonToGroup(@PathVariable Long id, @RequestParam("personId") Long personId) {
        Map<Object, Object> model = new HashMap<>();
        try {
            groupsService.addPersonToGroup(id, personId);
        } catch (NoSuchPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } catch (WrongPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createGroup(@RequestBody GroupsDTO groupsDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            groupsService.validateGroup(groupsDTO);
            groupsService.addNewGroup(groupsDTO);
        } catch (InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<?> deleteGroup(@RequestParam("groupId") Long groupId) {
        Map<Object, Object> model = new HashMap<>();
        try {
            groupsService.deleteGroup(groupId);
        } catch (WrongPersonException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "update", produces = "application/json")
    public ResponseEntity<?> updateGroup(@RequestBody GroupsDTO groupsDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            groupsService.validateGroup(groupsDTO);
            groupsService.updateGroup(groupsDTO);
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
