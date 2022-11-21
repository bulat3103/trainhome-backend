package com.example.trainhome.userservice.controllers;

import com.example.trainhome.userservice.dto.GroupsDTO;
import com.example.trainhome.userservice.services.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "groups")
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @CrossOrigin
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<?> createGroup(@RequestBody GroupsDTO groupsDTO) {
        if (!groupsService.validateGroup(groupsDTO)) return ResponseEntity.badRequest().body("Невалидные данные!");
        groupsService.addNewGroup(groupsDTO);
        return ResponseEntity.ok().body("Новая группа создана!");
    }
}
