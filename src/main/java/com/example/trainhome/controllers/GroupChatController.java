package com.example.trainhome.controllers;


import com.example.trainhome.dto.GroupChatDTO;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.services.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/groupchat")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    @CrossOrigin
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> createGroupChat() {
        Map<Object, Object> model = new HashMap<>();
        Long id = groupChatService.createGroupChat();
        model.put("chat", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/check", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> check(@RequestParam Long personId) {
        Map<Object, Object> model = new HashMap<>();
        model.put("check", groupChatService.check(personId));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/add/{id}/{personId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> addPeopleToGroupChat(@PathVariable("id") Long id, @PathVariable("personId") Long peopleToAdd) {
        Map<Object, Object> model = new HashMap<>();
        groupChatService.addPeopleToGroupChat(id, peopleToAdd);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateGroupChat(GroupChatDTO groupChatDTO) throws InvalidDataException, WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        groupChatService.updateGroupChat(groupChatDTO);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/delete/{id}/{personId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> deletePersonInGroupChat(@PathVariable("id") Long id, @PathVariable("personId") Long personId, GroupChatDTO groupChatDTO) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        groupChatService.deletePersonInGroupChat(id, personId);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/person/{personId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllGroupChatByPersonId(@PathVariable("personId") Long personId) {
        Map<Object, Object> model = new HashMap<>();
        List<GroupChatDTO> toReturn = groupChatService.getAllGroupChatByPersonId(personId);
        model.put("chat", toReturn);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/{groupId}/participants", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllPersonInGroupChat(@PathVariable("groupId") Long groupId) throws WrongPersonException {
        Map<Object, Object> model = new HashMap<>();
        List<PersonDTO> toReturn = groupChatService.getAllPersonInGroupChat(groupId);
        model.put("chat", toReturn);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
