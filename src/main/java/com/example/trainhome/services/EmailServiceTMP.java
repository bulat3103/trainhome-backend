package com.example.trainhome.services;

import com.example.trainhome.entities.GroupPerson;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Training;
import com.example.trainhome.repositories.GroupPersonRepository;
import com.example.trainhome.repositories.PersonRepository;
import com.example.trainhome.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailServiceTMP{

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private GroupPersonRepository groupPersonRepository;

    @Autowired
    private PersonRepository personRepository;

    public void toEmail() throws MessagingException {
        List<Training> listTraining = trainingRepository.getAllByToday();
        for(Training training : listTraining){
            List<GroupPerson> groupPersonList = groupPersonRepository.getAllByGroupId(training.getGroupId().getId());
            for(GroupPerson groupPerson : groupPersonList){
                sendEmail(groupPerson);
            }
        }
    }

    private void sendEmail(GroupPerson groupPerson) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom("zhamwan@gmail.com");
        message.setRecipients(Message.RecipientType.TO,groupPerson.getId().getPersonId().getEmail());
        message.setSubject("тренировка");
        message.setText("у вас сегодня тренировка");
        emailSender.send(message);
    }

}
