package com.example.trainhome.services;

import com.example.trainhome.dto.CreditDTO;
import com.example.trainhome.entities.Credit;
import com.example.trainhome.entities.Person;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.repositories.CoachRepository;
import com.example.trainhome.repositories.CreditRepository;
import com.example.trainhome.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

@Service
public class CreditService {

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoachRepository coachRepository;

    public void createCredit(CreditDTO creditDTO) throws InvalidDataException {
        Person person = personRepository.getById(creditDTO.getPersonId());
        if (person == null) {
            throw new InvalidDataException("Такого пользователя не сущесвует");
        }
        creditRepository.save(new Credit(
                0,
                creditDTO.getCommonCountMonths(),
                Date.valueOf(LocalDate.now().plusMonths(1)),
                creditDTO.getMoney(),
                person,
                coachRepository.getById(creditDTO.getCoachId())
        ));
    }
}
