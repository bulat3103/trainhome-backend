package com.example.trainhome.controllers;

import com.example.trainhome.dto.TransactionsDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @CrossOrigin
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionsDTO transactionsDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            transactionsService.createTransaction(transactionsDTO);
        } catch (InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllTransactions() {
        Map<Object, Object> model = new HashMap<>();
        model.put("transactions", transactionsService.getCoachListTransaction());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/sum/{coachId}", produces = "application/json")
    public ResponseEntity<?> getCoachMoney(@PathVariable("coachId") Long coachId) {
        Map<Object, Object> model = new HashMap<>();
        model.put("total", transactionsService.getCoachMoney(coachId));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
