package com.example.trainhome.userservice.services;

import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.userservice.dto.PersonDTO;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService{

    @Autowired
    private PersonRepository personRepository;


    public PersonDTO findByEmail(String email){
        return PersonDTO.PersonToPersonDTO(personRepository.findByEmail(email));
    }

    public PersonDTO findById(Long id){
        return PersonDTO.PersonToPersonDTO(personRepository.findPersonById(id));
    }

    public Person findPersonById(Long id){
        return personRepository.findPersonById(id);
    }

    public PersonDTO update(PersonDTO personDTO) throws NoSuchPersonException{
        Person person = findPersonById(personDTO.getId());
        if(person == null) throw new NoSuchPersonException("Не найден пользователь с email:" + personDTO.getEmail());
        return PersonDTO.PersonToPersonDTO(personRepository.updatePerson(person.getId(), person.getName(), person.getImage(),
                person.getPhoneNumber(), person.getBirthday()));
    }

    public PersonDTO delete(PersonDTO personDTO) throws  NoSuchPersonException{
        Person person = findPersonById(personDTO.getId());
        if(person == null) throw new NoSuchPersonException("Не найден пользователь с email:" + personDTO.getEmail());
        return PersonDTO.PersonToPersonDTO(personRepository.deletePerson(person.getId()));
    }
}
