package com.example.trainhome.controllers;

import com.example.trainhome.configuration.RoleConfig;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.services.AuthService;
import com.example.trainhome.dto.AuthRequestDTO;
import com.example.trainhome.dto.RegisterRequestDTO;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController(value = "auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private HttpServletRequest context;

    @CrossOrigin
    @PostMapping(value = "register", produces = "application/json")
    public ResponseEntity<?> registration(@RequestBody RegisterRequestDTO requestDTO, @RequestParam(name = "role") String role) {
        Map<Object, Object> model = new HashMap<>();
        try {
            authService.validateRegisterRequestDTO(requestDTO);
            Person newPerson = authService.addNewPerson(requestDTO, RoleConfig.valueOf(role.toUpperCase()).toString());
            String token = authService.createSession(newPerson);
            if (role.toUpperCase().equals(RoleConfig.ROLE_COACH.toString())) {
                authService.fillCoach(requestDTO, newPerson.getId());
            }
            model.put("token", token);
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
            String token = authService.authorizePerson(authRequestDTO);
            model.put("token", token);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (NoSuchPersonException | InvalidDataException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PostMapping(value = "logout", produces = "application/json")
    public ResponseEntity<?> logout() {
        Map<Object, Object> model = new HashMap<>();
        Session session = (Session) (context.getAttribute("session"));
        authService.closeSession(session);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
