package com.example.trainhome.services;

import com.example.trainhome.dto.GroupChatDTO;
import com.example.trainhome.dto.ListMessagesDTO;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.entities.ListMessage;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.exceptions.NoSuchGroupChatException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.repositories.GroupChatRepository;
import com.example.trainhome.repositories.ListMessagesRepository;
import com.example.trainhome.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListMessagesService {

    @Autowired
    private ListMessagesRepository listMessagesRepository;

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<ListMessagesDTO> findAllMessagesInChat(GroupChatDTO groupChatDTO) throws NoSuchGroupChatException {
        if(groupChatRepository.findById(groupChatDTO.getId()) == null) throw new NoSuchGroupChatException("Групповой чат не найден");
        List<ListMessage> listMessageList = listMessagesRepository.getListMessageByChatId(groupChatDTO.getId());
        List<ListMessagesDTO> listToReturn = new ArrayList<>();
        for(ListMessage listMessage : listMessageList){
            listToReturn.add(new ListMessagesDTO(listMessage.getId(), groupChatDTO.getId(), PersonDTO.PersonToPersonDTO(listMessage.getPersonId()),
                    listMessage.getContent(), listMessage.getDateCreate()));
        }
        return listToReturn;
    }

    public Long createListMessages(ListMessagesDTO listMessagesDTO) throws WrongPersonException{
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(person.getId() != listMessagesDTO.getPersonDTO().getId()) throw new WrongPersonException("Нет прав для этого действия!");
        ListMessage listMessage = new ListMessage(groupChatRepository.findGroupChatById(listMessagesDTO.getGroupChatId()), personRepository.getById(person.getId()),
                listMessagesDTO.getContent(), listMessagesDTO.getDateCreate());
        listMessagesRepository.save(listMessage);
        return listMessage.getId();
    }
}
