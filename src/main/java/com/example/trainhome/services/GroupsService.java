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
import com.example.trainhome.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<GroupsDTO> getAllGroups() {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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
                    PersonDTO.PersonToPersonDTO(group.getCoachId().getPersonId()),
                    group.getSportSphereId().getName(),
                    group.getMaxCount(),
                    group.getTrainsLeft()
            ));
        }
        return toReturn;
    }

    public List<PersonDTO> getPersonsInGroup(Long groupId) {
        List<Person> personList = personRepository.getAllPersonsInGroup(groupId);
        List<PersonDTO> toReturn = new ArrayList<>();
        for (Person person : personList) {
            toReturn.add(PersonDTO.PersonToPersonDTO(person));
        }
        return toReturn;
    }

    public void addPersonToGroup(Long groupId, Long personId) throws WrongPersonException, NoSuchPersonException, InvalidDataException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Groups group = groupsRepository.getById(groupId);
        long coachId = group.getCoachId().getPersonId().getId();
        if (person.getId() != coachId) {
            throw new WrongPersonException("Нет прав для этого действия!");
        }
        Person toAdd = personRepository.getById(personId);
        if (toAdd == null) throw new NoSuchPersonException("Такого пользователя не существует!");
        if (Objects.equals(group.getCount(), group.getMaxCount())) throw new InvalidDataException("Группа заполнена");
        groupPersonRepository.save(new GroupPerson(
                new GroupPersonId(group, toAdd)
        ));
    }

    public Long addNewGroup(GroupsDTO groupsDTO) {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return groupsRepository.save(new Groups(
                groupsDTO.getName(),
                coachRepository.getById(person.getId()),
                sportSphereRepository.getByName(groupsDTO.getSportSphereName()),
                groupsDTO.getMaxCount(),
                0,
                groupsDTO.getTrainsLeft()
        )).getId();
    }

    public void deleteGroup(Long id) throws WrongPersonException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Groups group = groupsRepository.getById(id);
        long personId = group.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("Нет прав для этого действия!");
        }
        groupsRepository.deleteById(id);
    }

    public void updateGroup(GroupsDTO groupsDTO) throws WrongPersonException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Groups group = groupsRepository.getById(groupsDTO.getId());
        long personId = group.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("Нет прав для этого действия!");
        }
        groupsRepository.updateGroup(groupsDTO.getId(), group.getName(), groupsDTO.getMaxCount(), groupsDTO.getTrainsLeft());
    }
}
