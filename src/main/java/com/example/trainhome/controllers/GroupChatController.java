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
@RequestMapping("/groupchat")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    @CrossOrigin
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> createGroupChat(GroupChatDTO groupChatDTO){
        Map<Object, Object> model = new HashMap<>();
        try{
            Long id = groupChatService.createGroupChat(groupChatDTO);
            model.put("chat", id);
        } catch (WrongPersonException e) {
            model.put("chat", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateGroupChat(GroupChatDTO groupChatDTO){
        Map<Object, Object> model = new HashMap<>();
        try{
            groupChatService.updateGroupChat(groupChatDTO);
        } catch (WrongPersonException e) {
            model.put("chat", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.NOT_ACCEPTABLE);
        } catch (InvalidDataException e) {
            model.put("chat", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> deletePersonInGroupChat(GroupChatDTO groupChatDTO){
        Map<Object, Object> model = new HashMap<>();
        try{
            groupChatService.deletePersonInGroupChat(groupChatDTO);
        } catch (InvalidDataException e) {
            model.put("chat", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "list", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllGroupChatByPersonId(){
        Map<Object, Object> model = new HashMap<>();
            List<GroupChatDTO> toReturn = groupChatService.getAllGroupChatByPersonId();
            model.put("chat", toReturn);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "allchat", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllPersonInGroupChat(GroupChatDTO groupChatDTO){
        Map<Object, Object> model = new HashMap<>();
        List<PersonDTO> toReturn = groupChatService.getAllPersonInGroupChat(groupChatDTO);
        model.put("chat", toReturn);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
