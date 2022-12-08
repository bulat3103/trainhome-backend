package com.example.trainhome.services;

import com.example.trainhome.dto.ImageDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.repositories.ImageRepository;
import com.example.trainhome.repositories.PersonRepository;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ImageRepository imageRepository;

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
            throw new WrongPersonException("Нет прав для этого действия!");
        }
        if(person == null) throw new NoSuchPersonException("Пользователь не найден!");
        return PersonDTO.PersonToPersonDTO(personRepository.updatePerson(person.getId(), person.getName(),
                person.getPhoneNumber(), person.getBirthday()));
    }

    public void updateImage(ImageDTO imageDTO) throws InvalidDataException, WrongPersonException {
        Person personFromSession = ((Session) context.getAttribute("session")).getPerson();
        if(imageDTO.getHex() == null) throw new InvalidDataException("Фотография не может быть пустой");
        if(!Objects.equals(personFromSession.getImage().getId(), imageDTO.getId())) throw new WrongPersonException("Нет прав для этого действия!");
        imageRepository.updateById(imageDTO.getId(), imageDTO.getHex());
    }

    public void delete(Long id) throws NoSuchPersonException{
        Person person = personRepository.getById(id);
        if(person == null) throw new NoSuchPersonException("Пользователь не найден!");
        personRepository.deletePerson(id);
    }
}
