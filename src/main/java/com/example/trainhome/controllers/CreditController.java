package com.example.trainhome.controllers;

import com.example.trainhome.dto.CreditDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/credit")
public class CreditController {
    @Autowired
    private CreditService creditService;

    @CrossOrigin
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createCredit(@RequestBody CreditDTO creditDTO) throws InvalidDataException {
        Map<Object, Object> model = new HashMap<>();
        creditService.createCredit(creditDTO);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
