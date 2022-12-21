package com.example.trainhome.services;


import com.example.trainhome.dto.GroupChatDTO;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.entities.GroupChat;
import com.example.trainhome.entities.ListPerson;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.entities.compositeKeys.ListPersonId;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.repositories.GroupChatRepository;
import com.example.trainhome.repositories.ListPersonRepository;
import com.example.trainhome.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GroupChatService {

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private ListPersonRepository listPersonRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    public Long createGroupChat() {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        GroupChat groupChat = new GroupChat(person,
                "");
        groupChatRepository.save(groupChat);
        return groupChat.getId();
    }

    public boolean check(Long personId) {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Long> first = listPersonRepository.getAllGroupChatByPersonId(person.getId());
        List<Long> second = listPersonRepository.getAllGroupChatByPersonId(personId);
        for (Long chatId : first) {
            if (second.contains(chatId) && listPersonRepository.getAllPersonIdInChat(chatId).size() == 2)
                return true;
        }
        return false;
    }

    public void updateGroupChat(GroupChatDTO groupChatDTO) throws WrongPersonException, InvalidDataException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!Objects.equals(person.getId(), groupChatDTO.getPersonDTO().getId())) throw new WrongPersonException("Нет прав для этого действия!");
        if(groupChatDTO.getName().length() == 0 || groupChatDTO.getName() == null) throw new InvalidDataException("Название группы не может быть пустым");
        groupChatRepository.updateGroupChatName(groupChatDTO.getId(), groupChatDTO.getName());
    }

    public void deletePersonInGroupChat(GroupChatDTO groupChatDTO) throws InvalidDataException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(groupChatRepository.findById(groupChatDTO.getId()) == null) throw new InvalidDataException("Группа не найдена");
        groupChatRepository.deletePersonInGroupChat(groupChatDTO.getId(), person.getId());
    }

    public List<GroupChatDTO> getAllGroupChatByPersonId(){
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Long> listPerson = listPersonRepository.getAllGroupChatByPersonId(person.getId());
        List<GroupChatDTO> list = new ArrayList<>();
        for(Long id : listPerson){
            GroupChat chat = groupChatRepository.findGroupChatById(id);
            List<Long> persons = listPersonRepository.getAllPersonIdInChat(id);
            list.add(new GroupChatDTO(chat.getId(),
                    PersonDTO.PersonToPersonDTO(chat.getPersonId()),
                    persons.size() > 1 ? "Чат группы " + id : personRepository.findPersonById(persons.get(0)).getName(),
                    chat.getPersonId().getId()));
        }
        return list;
    }

    public List<PersonDTO> getAllPersonInGroupChat(GroupChatDTO groupChatDTO) throws WrongPersonException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Person> personList = groupChatRepository.getAllPersonIdGroupChat(groupChatDTO.getId());
        Boolean flag = false;
        for(Person per : personList){
            if(person.equals(per)){
                flag = true;
                break;
            }
        }
        if(!flag) throw new WrongPersonException("Нет прав для этого действия!");
        List<PersonDTO> list = new ArrayList<>();
        for(Person personInList : personList){
            list.add(PersonDTO.PersonToPersonDTO(personInList));
        }
        return list;
    }

    public void addPeopleToGroupChat(Long id, Long personId) {
        listPersonRepository.save(new ListPerson(new ListPersonId(
                groupChatRepository.findGroupChatById(id),
                personRepository.getById(personId)
        )));
    }
}
