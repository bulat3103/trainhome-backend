package com.example.trainhome.userservice.services;

import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.userservice.dto.PersonDTO;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService{
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void addPerson(Person person){
        personRepository.save(person);
    }

    public Person findByPersonEmail(String email){
        return personRepository.findByEmail(email);
    }

    public Person update(PersonDTO personDTO) throws NoSuchPersonException{
        Person person = findByPersonEmail(personDTO.getEmail());
        if(person == null) throw new NoSuchPersonException("Не найден пользователь с email:" + personDTO.getEmail());
        person.setBirthday(personDTO.getBirthday());
        person.setName(personDTO.getName());
        person.setImage(personDTO.getImage());
        person.setPhoneNumber(personDTO.getPhoneNumber());
        return person;
    }
}
