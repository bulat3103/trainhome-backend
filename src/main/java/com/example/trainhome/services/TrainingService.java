package com.example.trainhome.services;

import com.example.trainhome.dto.GroupsDTO;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.dto.TrainingDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.repositories.PersonRepository;
import com.example.trainhome.repositories.TrainingRepository;
import com.example.trainhome.entities.Person;
import com.example.trainhome.entities.Session;
import com.example.trainhome.entities.Training;
import com.example.trainhome.repositories.CoachRepository;
import com.example.trainhome.repositories.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GroupsRepository groupsRepository;

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

    public Long addTraining(TrainingDTO trainingDTO) {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Training training = new Training(
                trainingDTO.getTrainingDate(),
                coachRepository.getById(person.getId()),
                trainingDTO.getLink(),
                groupsRepository.getById(trainingDTO.getGroupsDTO().getId())
        );
        trainingRepository.save(training);
        return training.getId();
    }

    public void deleteTraining(Long id) throws WrongPersonException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Training training = trainingRepository.getById(id);
        long personId = training.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("INVALID PERSON!");
        }
        trainingRepository.deleteById(id);
    }

    public void updateTraining(TrainingDTO trainingDTO) throws WrongPersonException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Training training = trainingRepository.getById(trainingDTO.getId());
        long personId = training.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("INVALID PERSON!");
        }
        trainingRepository.updateTraining(trainingDTO.getId(), trainingDTO.getTrainingDate(), trainingDTO.getLink());
    }

    public List<TrainingDTO> getAllTrainingByPerson(){
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Training> trainingList;
        if (person.getRoleId().getName().equals("COACH")) {
            trainingList = trainingRepository.getAllByCoachId(person.getId());
        } else {
            trainingList = trainingRepository.getAllByPersonId(person.getId());
        }
        List<TrainingDTO> trainingDTOList = new ArrayList<>();
        for (Training training : trainingList){
            trainingDTOList.add(new TrainingDTO(training.getId(), training.getTrainingDate(), PersonDTO.PersonToPersonDTO(training.getCoachId().getPersonId()),
                    training.getLink(), GroupsDTO.GroupsToGroupsDTO(training.getGroupId())));
        }
        return trainingDTOList;
    }
}
