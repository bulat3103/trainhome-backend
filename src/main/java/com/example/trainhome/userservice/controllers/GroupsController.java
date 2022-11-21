package com.example.trainhome.userservice.controllers;

import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.userservice.dto.GroupsDTO;
import com.example.trainhome.userservice.services.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "groups")
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

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
}
