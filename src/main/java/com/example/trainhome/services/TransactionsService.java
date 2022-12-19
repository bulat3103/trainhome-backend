package com.example.trainhome.services;

import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.dto.TransactionsDTO;
import com.example.trainhome.dto.TransactionsResponseDTO;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.entities.Transactions;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.repositories.CoachRepository;
import com.example.trainhome.repositories.PersonRepository;
import com.example.trainhome.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private PersonRepository personRepository;

    public void createTransaction(TransactionsDTO transactionsDTO) throws InvalidDataException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if ((long) person.getId() == transactionsDTO.getCoachId()) {
            if (Math.abs(transactionsDTO.getMoney()) > getCoachMoney()) throw new InvalidDataException("Недостаточно средств на счете!");
        }
        transactionsRepository.save(new Transactions(
                transactionsDTO.getDate(),
                coachRepository.getById(transactionsDTO.getCoachId()),
                transactionsDTO.getMoney(),
                personRepository.getById(person.getId())
        ));
    }

    public List<TransactionsResponseDTO> getCoachListTransaction() {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Transactions> list = transactionsRepository.getAllByCoachId(person.getId());
        List<TransactionsResponseDTO> toReturn = new ArrayList<>();
        for (Transactions transaction : list) {
            toReturn.add(new TransactionsResponseDTO(
                    transaction.getDate(),
                    transaction.getMoney(),
                    PersonDTO.PersonToPersonDTO(transaction.getPersonId())
            ));
        }
        return toReturn;
    }

    public Integer getCoachMoney() {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return transactionsRepository.getCoachMoney(person.getId());
    }
}
