package com.example.trainhome.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "transaction")
public class TransactionsController {

    @CrossOrigin
    @PostMapping(value = "pay", produces = "application/json")
    public ResponseEntity<?> pay() {

    }
}
