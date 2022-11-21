package com.example.trainhome.userservice.services;

import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.userservice.dto.GroupsDTO;
import com.example.trainhome.userservice.entities.Groups;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.entities.Session;
import com.example.trainhome.userservice.repositories.CoachRepository;
import com.example.trainhome.userservice.repositories.GroupsRepository;
import com.example.trainhome.userservice.repositories.SportSphereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class GroupsService {
    @Autowired
    private SportSphereRepository sportSphereRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private HttpServletRequest context;

    public void validateGroup(GroupsDTO groupsDTO) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (groupsDTO.getName() == null || groupsDTO.getName().equals("")) {
            message.append("Название не может быть пустым!\n");
            valid = false;
        }
        if (sportSphereRepository.getByName(groupsDTO.getSportSphereName()) == null) {
            message.append("Такого вида спорта нет!\n");
            valid = false;
        }
        if (groupsDTO.getMaxCount() == null || groupsDTO.getMaxCount() <= 0) {
            message.append("Количество людей должно быть положительным!\n");
            valid = false;
        }
        if (groupsDTO.getTrainsLeft() == null || groupsDTO.getTrainsLeft() <= 0) {
            message.append("количество тренировок должно быть положительным!\n");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }

    public void addNewGroup(GroupsDTO groupsDTO) {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        groupsRepository.save(new Groups(
                groupsDTO.getName(),
                coachRepository.getByPersonId(person.getId()),
                sportSphereRepository.getByName(groupsDTO.getSportSphereName()),
                0,
                groupsDTO.getMaxCount(),
                groupsDTO.getTrainsLeft()
        ));
    }

    public void deleteGroup(Long id) throws WrongPersonException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        Groups group = groupsRepository.getById(id);
        long personId = group.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("INVALID PERSON!");
        }
        groupsRepository.deleteById(id);
    }
}
