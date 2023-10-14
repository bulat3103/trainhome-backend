package com.example.trainhome.controllers;


import com.example.trainhome.dto.ListMessagesDTO;
import com.example.trainhome.exceptions.NoSuchGroupChatException;
import com.example.trainhome.services.ListMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/messages")
public class ListMessagesController {

    @Autowired
    private ListMessagesService listMessagesService;

    @CrossOrigin
    @GetMapping(value = "/{groupId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllMessagesInChat(@PathVariable("groupId") Long groupId) throws NoSuchGroupChatException {
        Map<Object, Object> model = new HashMap<>();
        List<ListMessagesDTO> list = listMessagesService.findAllMessagesInChat(groupId);
        model.put("messages", list);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> createMessage(@RequestBody ListMessagesDTO listMessagesDTO) {
        Map<Object, Object> model = new HashMap<>();
        Long id = listMessagesService.createListMessages(listMessagesDTO);
        model.put("id", id);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
