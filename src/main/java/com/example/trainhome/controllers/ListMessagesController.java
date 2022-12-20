package com.example.trainhome.controllers;


import com.example.trainhome.dto.GroupChatDTO;
import com.example.trainhome.dto.ListMessagesDTO;
import com.example.trainhome.exceptions.NoSuchGroupChatException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.services.ListMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/messages")
public class ListMessagesController {

    @Autowired
    private ListMessagesService listMessagesService;

    @CrossOrigin
    @PostMapping(value = "list", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllMessagesInChat(@RequestBody GroupChatDTO groupChatDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            List<ListMessagesDTO> list = listMessagesService.findAllMessagesInChat(groupChatDTO);
            model.put("messages", list);
        } catch (NoSuchGroupChatException e) {
            model.put("messages", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
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
