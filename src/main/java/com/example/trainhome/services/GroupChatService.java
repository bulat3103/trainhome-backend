package com.example.trainhome.services;


import com.example.trainhome.dto.GroupChatDTO;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.entities.GroupChat;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.repositories.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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
        if(person.getId() != groupChatDTO.getPersonDTO().getId()) throw new WrongPersonException("Нет прав для этого действия!");
        GroupChat groupChat = new GroupChat(personService.findPersonById(groupChatDTO.getPersonDTO().getId()),
                groupChatDTO.getName());
        groupChatRepository.save(groupChat);
        return groupChat.getId();
    }


}
