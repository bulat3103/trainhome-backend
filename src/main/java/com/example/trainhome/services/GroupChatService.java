package com.example.trainhome.services;


import com.example.trainhome.dto.GroupChatDTO;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.entities.GroupChat;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.repositories.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PersonService personService;

    @Autowired
    private HttpServletRequest context;

    public Long createGroupChat(GroupChatDTO groupChatDTO) throws WrongPersonException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        if(!Objects.equals(person.getId(), groupChatDTO.getPersonDTO().getId())) throw new WrongPersonException("Нет прав для этого действия!");
        GroupChat groupChat = new GroupChat(personService.findPersonById(groupChatDTO.getPersonDTO().getId()),
                groupChatDTO.getName());
        groupChatRepository.save(groupChat);
        return groupChat.getId();
    }

    public void updateGroupChat(GroupChatDTO groupChatDTO) throws WrongPersonException, InvalidDataException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        if(!Objects.equals(person.getId(), groupChatDTO.getPersonDTO().getId())) throw new WrongPersonException("Нет прав для этого действия!");
        if(groupChatDTO.getName().length() == 0 || groupChatDTO.getName() == null) throw new InvalidDataException("Название группы не может быть пустым");
        groupChatRepository.updateGroupChatName(groupChatDTO.getId(), groupChatDTO.getName());
    }

    public void deletePersonInGroupChat(GroupChatDTO groupChatDTO) throws InvalidDataException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        if(groupChatRepository.findById(groupChatDTO.getId()) == null) throw new InvalidDataException("Группа не найдена");
        groupChatRepository.deletePersonInGroupChat(groupChatDTO.getId(), person.getId());
    }

    public List<GroupChatDTO> getAllGroupChatByPersonId(){
        Person person = ((Session) context.getAttribute("session")).getPerson();
        List<GroupChat> GroupChat = groupChatRepository.getAllGroupChatByPersonId(person.getId());
        List<GroupChatDTO> list = new ArrayList<>();
        for(GroupChat groupChat : GroupChat){
            list.add(new GroupChatDTO(groupChat.getId(),
                    PersonDTO.PersonToPersonDTO(groupChat.getPersonId()),
                    groupChat.getName()));
        }
        return list;
    }

    public List<PersonDTO> getAllPersonInGroupChat(GroupChatDTO groupChatDTO) throws WrongPersonException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
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
}
