package com.example.trainhome.controllers;

import com.example.trainhome.configuration.RoleConfig;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.services.AuthService;
import com.example.trainhome.dto.AuthRequestDTO;
import com.example.trainhome.dto.RegisterRequestDTO;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.tokens.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AuthService authService;

    @CrossOrigin
    @PostMapping(value = "register", produces = "application/json")
    public ResponseEntity<?> registration(@RequestBody RegisterRequestDTO requestDTO, @RequestParam(name = "role") String role) {
        Map<Object, Object> model = new HashMap<>();
        try {
            authService.validateRegisterRequestDTO(requestDTO);
            Person newPerson = authService.addNewPerson(requestDTO, role.equals("ROLE_CLIENT") ? "CLIENT" : "COACH");
            String token = tokenUtils.generateToken(newPerson.getEmail());
            if (RoleConfig.valueOf(role.toUpperCase()).toString().equals(RoleConfig.ROLE_COACH.toString())) {
                authService.fillCoach(requestDTO, newPerson.getId());
            }
            model.put("token", token);
            model.put("role", newPerson.getRoleId().getName());
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping(value = "login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO) {
        Map<Object, Object> model = new HashMap<>();
        try {
            Map<Object, Object> map = authService.authorizePerson(authRequestDTO);
            model.putAll(map);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (NoSuchPersonException | InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
    }
}
