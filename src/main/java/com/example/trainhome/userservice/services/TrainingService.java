package com.example.trainhome.userservice.services;

import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.userservice.dto.TrainingDTO;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.entities.Session;
import com.example.trainhome.userservice.entities.Training;
import com.example.trainhome.userservice.repositories.CoachRepository;
import com.example.trainhome.userservice.repositories.GroupsRepository;
import com.example.trainhome.userservice.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private HttpServletRequest context;

    public void validateTraining(TrainingDTO trainingDTO) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (trainingDTO.getTrainingDate().before(Date.valueOf(LocalDate.now()))) {
            message.append("Тренировка не может назначаться на прошедшую дату!");
            valid = false;
        }
        if (trainingDTO.getLink() == null || trainingDTO.getLink().equals("")) {
            message.append("Ссылка на тренировку не может быть пустой!");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }

    public void addTraining(TrainingDTO trainingDTO) {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        trainingRepository.save(new Training(
                trainingDTO.getTrainingDate(),
                coachRepository.getByPersonId(person.getId()),
                trainingDTO.getLink(),
                groupsRepository.getById(trainingDTO.getGroupId())
        ));
    }
}
