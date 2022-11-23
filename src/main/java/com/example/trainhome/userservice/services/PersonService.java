package com.example.trainhome.userservice.services;

import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.userservice.dto.PersonDTO;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.entities.Session;
import com.example.trainhome.userservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HttpServletRequest context;


    public PersonDTO findByEmail(String email){
        return PersonDTO.PersonToPersonDTO(personRepository.findByEmail(email));
    }

    public PersonDTO findById(Long id){
        return PersonDTO.PersonToPersonDTO(personRepository.findPersonById(id));
    }

    public Person findPersonById(Long id){
        return personRepository.findPersonById(id);
    }

    public PersonDTO update(PersonDTO personDTO) throws NoSuchPersonException, WrongPersonException {
        Person personFromSession = ((Session) context.getAttribute("session")).getPerson();
        Person person = findPersonById(personDTO.getId());
        if ((long) personDTO.getId() != personFromSession.getId()) {
            throw new WrongPersonException("У вас нет прав для этого действия!");
        }
        if(person == null) throw new NoSuchPersonException("Пользователь не найден!");
        return PersonDTO.PersonToPersonDTO(personRepository.updatePerson(person.getId(), person.getName(), person.getImage(),
                person.getPhoneNumber(), person.getBirthday()));
    }

    public void delete(Long id) throws NoSuchPersonException{
        Person person = personRepository.getById(id);
        if(person == null) throw new NoSuchPersonException("Пользователь не найден!");
        personRepository.deletePerson(id);
    }
}
