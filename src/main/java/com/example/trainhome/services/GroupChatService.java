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

    public void deletePersonInGroupChat(GroupChatDTO groupChatDTO){

    }
}
