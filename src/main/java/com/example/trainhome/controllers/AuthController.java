package com.example.trainhome.controllers;

import com.example.trainhome.configuration.RoleConfig;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.services.AuthService;
import com.example.trainhome.dto.AuthRequestDTO;
import com.example.trainhome.dto.RegisterRequestDTO;
import com.example.trainhome.entities.Person;
import com.example.trainhome.tokens.TokenUtils;
import com.example.trainhome.validators.AuthRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AuthService authService;

    @CrossOrigin
    @PostMapping(value = "register", produces = "application/json")
    public ResponseEntity<?> registration(
            @RequestParam(name = "role") String role,
            @RequestBody RegisterRequestDTO requestDTO) throws InvalidDataException
    {
        Map<Object, Object> model = new HashMap<>();
        AuthRequestValidator.validateRegister(role, requestDTO);
        Person newPerson = authService.addNewPerson(requestDTO, role.equals("ROLE_CLIENT") ? "CLIENT" : "COACH");
        String token = tokenUtils.generateToken(newPerson.getEmail());
        if (RoleConfig.valueOf(role.toUpperCase()).toString().equals(RoleConfig.ROLE_COACH.toString())) {
            authService.fillCoach(requestDTO, newPerson.getId());
        }
        model.put("token", token);
        model.put("role", newPerson.getRoleId().getName());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO) throws NoSuchPersonException, InvalidDataException {
        AuthRequestValidator.validateLogin(authRequestDTO);
        Map<Object, Object> map = authService.authorizePerson(authRequestDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
