package com.example.trainhome.services;

import com.example.trainhome.entities.Image;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.repositories.*;
import com.example.trainhome.dto.AuthRequestDTO;
import com.example.trainhome.dto.RegisterRequestDTO;
import com.example.trainhome.dto.SportPriceDTO;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.entities.SportSphereCoachPrice;
import com.example.trainhome.entities.compositeKeys.SportSphereCoachPriceId;
import com.example.trainhome.tokens.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final String emailCheck="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private SportSphereCoachPriceRepository sportSphereCoachPriceRepository;

    @Autowired
    private SportSphereRepository sportSphereRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ImageRepository imageRepository;

    public Person addNewPerson(RegisterRequestDTO requestDTO, String role) {
        Person newPerson = new Person();
        newPerson.setPassword(requestDTO.getPassword());
        newPerson.setName(requestDTO.getName());
        Image image = new Image();
        image.setHex(requestDTO.getImage().getHex());
        imageRepository.save(image);
        newPerson.setImage(image);
        newPerson.setPhoneNumber(requestDTO.getPhoneNumber());
        newPerson.setEmail(requestDTO.getEmail());
        newPerson.setBirthday(requestDTO.getBirthday());
        newPerson.setSex(requestDTO.isSex());
        newPerson.setRoleId(roleRepository.findByName(role));
        return personRepository.save(newPerson);
    }

    public Map<Object, Object> authorizePerson(AuthRequestDTO authRequestDTO) throws NoSuchPersonException, InvalidDataException {
        Person personFromDB = findByEmail(authRequestDTO.getEmail());
        if (personFromDB == null) throw new NoSuchPersonException("Такого пользователя не существует!");
        else if (!authRequestDTO.getPassword().equals(personFromDB.getPassword())) throw new InvalidDataException("Неправильный пароль!");
        else {
            Map<Object, Object> map = new HashMap<>();
            map.put("token", tokenUtils.generateToken(personFromDB.getEmail()));
            map.put("role", personFromDB.getRoleId().getName());
            return map;
        }
    }

    public void fillCoach(RegisterRequestDTO requestDTO, Long personId) {
        coachRepository.fillCoach(personId, requestDTO.getInfo(), requestDTO.getAchievements());
        for (SportPriceDTO dto : requestDTO.getListPrices()) {
            sportSphereCoachPriceRepository.save(new SportSphereCoachPrice(
                    new SportSphereCoachPriceId(coachRepository.getById(personId), sportSphereRepository.getByName(dto.getSportName())),
                    dto.getPrice()
            ));
        }
    }

    private Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
