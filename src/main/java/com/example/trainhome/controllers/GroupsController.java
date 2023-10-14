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
@RequestMapping("/api/v1/groups")
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @CrossOrigin
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllGroups() {
        Map<Object, Object> model = new HashMap<>();
        model.put("groups", groupsService.getAllGroups());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/{groupId}", produces = "application/json")
    public ResponseEntity<?> getPersonsInGroup(@PathVariable("groupId") Long id) {
        Map<Object, Object> model = new HashMap<>();
        model.put("persons", groupsService.getPersonsInGroup(id));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/{groupId}/{personId}", produces = "application/json")
    public ResponseEntity<?> addPersonToGroup(@PathVariable("groupId") Long groupId, @PathVariable("personId") Long personId) throws NoSuchPersonException, InvalidDataException, WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        groupsService.addPersonToGroup(groupId, personId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createGroup(@RequestBody GroupsDTO groupsDTO) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        groupsService.validateGroup(groupsDTO);
        model.put("groupId", groupsService.addNewGroup(groupsDTO));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") Long groupId) throws WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        groupsService.deleteGroup(groupId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(produces = "application/json")
    public ResponseEntity<?> updateGroup(@RequestBody GroupsDTO groupsDTO) throws InvalidDataException, WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        groupsService.validateGroup(groupsDTO);
        groupsService.updateGroup(groupsDTO);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
