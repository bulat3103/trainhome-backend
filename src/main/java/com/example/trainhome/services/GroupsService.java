package com.example.trainhome.services;

import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.entities.GroupPerson;
import com.example.trainhome.entities.Groups;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.entities.compositeKeys.GroupPersonId;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.NoSuchPersonException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.repositories.*;
import com.example.trainhome.dto.GroupsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupsService {
    @Autowired
    private SportSphereRepository sportSphereRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GroupPersonRepository groupPersonRepository;

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

    public List<GroupsDTO> getAllGroups() {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        List<GroupsDTO> toReturn = new ArrayList<>();
        List<Groups> list;
        if (person.getRoleId().getName().equals("COACH")) {
            list = groupsRepository.getByCoachId(person.getId());
        } else {
            list = new ArrayList<>();
            List<GroupPerson> groupPersonList = groupPersonRepository.getAllByPersonId(person.getId());
            for (GroupPerson gp : groupPersonList) {
                list.add(gp.getId().getGroupId());
            }
        }
        for (Groups group : list) {
            toReturn.add(new GroupsDTO(
                    group.getId(),
                    group.getName(),
                    group.getSportSphereId().getName(),
                    group.getMaxCount(),
                    group.getTrainsLeft()
            ));
        }
        return toReturn;
    }

    public List<PersonDTO> getPersonsInGroup(Long groupId) {
        return null;
    }

    public void addPersonToGroup(Long groupId, Long personId) throws WrongPersonException, NoSuchPersonException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        Groups group = groupsRepository.getById(groupId);
        long coachId = group.getCoachId().getPersonId().getId();
        if (person.getId() != coachId) {
            throw new WrongPersonException("Нет прав для этого действия!");
        }
        Person toAdd = personRepository.getById(personId);
        if (toAdd == null) throw new NoSuchPersonException("Такого пользователя не существует!");
        groupPersonRepository.save(new GroupPerson(
                new GroupPersonId(group, toAdd)
        ));
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
            throw new WrongPersonException("Нет прав для этого действия!");
        }
        groupsRepository.deleteById(id);
    }

    public void updateGroup(GroupsDTO groupsDTO) throws WrongPersonException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        Groups group = groupsRepository.getById(groupsDTO.getId());
        long personId = group.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("Нет прав для этого действия!");
        }
        groupsRepository.updateGroup(groupsDTO.getId(), group.getName(), groupsDTO.getMaxCount(), groupsDTO.getTrainsLeft());
    }
}
