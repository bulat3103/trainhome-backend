package com.example.trainhome.userservice.services;

import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.userservice.dto.AuthRequestDTO;
import com.example.trainhome.userservice.dto.RegisterRequestDTO;
import com.example.trainhome.userservice.dto.SportPriceDTO;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.entities.Session;
import com.example.trainhome.userservice.entities.SportSphereCoachPrice;
import com.example.trainhome.userservice.entities.compositeKeys.SportSphereCoachPriceId;
import com.example.trainhome.userservice.repositories.*;
import com.example.trainhome.userservice.tokens.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private SportSphereCoachPriceRepository sportSphereCoachPriceRepository;

    @Autowired
    private SportSphereRepository sportSphereRepository;

    @Autowired
    private PersonRepository personRepository;

    public Person addNewPerson(RegisterRequestDTO requestDTO, String role) {
        Person newPerson = new Person();
        newPerson.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        newPerson.setName(requestDTO.getName());
        newPerson.setImage(requestDTO.getImage());
        newPerson.setPhoneNumber(requestDTO.getPhoneNumber());
        newPerson.setEmail(requestDTO.getEmail());
        newPerson.setBirthday(requestDTO.getBirthday());
        newPerson.setSex(requestDTO.isSex());
        newPerson.setRoleId(roleRepository.findByName(role));
        return newPerson;
    }

    public String authorizePerson(AuthRequestDTO authRequestDTO) throws NoSuchPersonException, InvalidDataException {
        Person personFromDB = findByEmail(authRequestDTO.getEmail());
        if (personFromDB == null) throw new NoSuchPersonException("Такого пользователя не существует!");
        else if (!passwordEncoder.matches(authRequestDTO.getPassword(), personFromDB.getPassword())) throw new InvalidDataException("Неправильный пароль!");
        else {
            return createSession(personFromDB);
        }
    }

    public void fillCoach(RegisterRequestDTO requestDTO, Long personId) {
        coachRepository.fillCoach(personId, requestDTO.getInfo(), requestDTO.getAchievements());
        for (SportPriceDTO dto : requestDTO.getListPrices()) {
            sportSphereCoachPriceRepository.save(new SportSphereCoachPrice(
                    new SportSphereCoachPriceId(coachRepository.getByPersonId(personId), sportSphereRepository.getByName(dto.getSportName())),
                    dto.getPrice()
            ));
        }
    }

    private Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public void validateRegisterRequestDTO(RegisterRequestDTO requestDTO) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (requestDTO.getPassword() == null || requestDTO.getPassword().equals("") || requestDTO.getPassword().length() < 5) {
            message.append("Пароль должен состоять минимум из 5 символов!");
            valid = false;
        }
        if (requestDTO.getName() == null || requestDTO.getName().equals("")) {
            message.append("ФИО не может быть пустым!");
            valid = false;
        }
        if (requestDTO.getPhoneNumber() == null || requestDTO.getPhoneNumber().equals("")
                || !requestDTO.getPhoneNumber().matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")) {
            message.append("Некорректный формат телефона!");
            valid = false;
        }
        if (requestDTO.getEmail() == null || requestDTO.getEmail().equals("")
                || !requestDTO.getEmail().matches("^[_A-Za-z\\d-\\\\+]+(\\\\.[_A-Za-z\\d-]+)*@[A-Za-z\\d-]+(\\\\.[A-Za-z\\d]+)*(\\\\.[A-Za-z]{2,})$")) {
            message.append("Некорректный формат email!");
            valid = false;
        }
        if (requestDTO.getBirthday() == null || requestDTO.getBirthday().after(Date.valueOf(LocalDate.now()))) {
            message.append("Некорректная дата рождения!");
            valid = false;
        }
        for (SportPriceDTO dto: requestDTO.getListPrices()) {
            if (dto.getPrice() <= 0) {
                message.append("Стоимость занятий должна быть положительным числом!");
                valid = false;
            }
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }

    public String createSession(Person person) {
        String token = TokenUtils.generate(person);
        Session session = new Session(person, token, java.util.Date.from(Instant.now()), false);
        sessionRepository.save(session);
        return token;
    }

    public void closeSession(Session session) {
        sessionRepository.setExpirationById(session.getId());
    }

    public void closeAllPersonSessions(Session session) {
        sessionRepository.setExpirationByPerson(session.getPerson().getId());
    }
}
