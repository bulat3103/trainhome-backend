package com.example.trainhome.userservice.services;

import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.RecommendationNotFoundException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.userservice.dto.EatCalendarDTO;
import com.example.trainhome.userservice.entities.EatCalendar;
import com.example.trainhome.userservice.entities.Person;
import com.example.trainhome.userservice.entities.Session;
import com.example.trainhome.userservice.repositories.CoachRepository;
import com.example.trainhome.userservice.repositories.EatCalendarRepository;
import com.example.trainhome.userservice.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EatCalendarService {

    @Autowired
    private EatCalendarRepository eatCalendarRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private HttpServletRequest context;

    public void validateRecommendation(EatCalendarDTO eatCalendarDTO) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (eatCalendarDTO.getDate().before(Date.valueOf(LocalDate.now()))) {
            message.append("Рекомендация не может назначаться на прошедшую дату!");
            valid = false;
        }
        if (eatCalendarDTO.getInfo() == null || eatCalendarDTO.getInfo().equals("")) {
            message.append("Рекомендация не может быть пустой!");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }

    public EatCalendarDTO getCoachRecommendation(Long personId, Date date) throws RecommendationNotFoundException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        EatCalendar recommendation = eatCalendarRepository.getByPersonIdAndCoachIdAndDate(person.getId(), personId, date);
        if (recommendation == null) {
            throw new RecommendationNotFoundException("Не было найдено записей!");
        }
        return new EatCalendarDTO(recommendation.getId(), recommendation.getInfo(), recommendation.getDate(),
                personId, null);
    }

    public List<EatCalendarDTO> getPersonRecommendation(Date date) throws RecommendationNotFoundException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        List<EatCalendar> list = eatCalendarRepository.getAllByPersonIdAndDate(person.getId(), date);
        if (list.isEmpty()) {
            throw new RecommendationNotFoundException("Не было найдено записей!");
        }
        List<EatCalendarDTO> listToReturn = new ArrayList<>();
        for (EatCalendar recommendation : list) {
            listToReturn.add(new EatCalendarDTO(recommendation.getId(), recommendation.getInfo(), recommendation.getDate(),
                    person.getId(), null));
        }
        return listToReturn;
    }

    public Long createRecommendation(EatCalendarDTO eatCalendarDTO) {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        EatCalendar eatCalendar = new EatCalendar(
                eatCalendarDTO.getInfo(),
                eatCalendarDTO.getDate(),
                personRepository.getById(eatCalendarDTO.getPersonId()),
                coachRepository.getByPersonId(person.getId())
        );
        eatCalendarRepository.save(eatCalendar);
        return eatCalendar.getId();
    }

    public void deleteRecommendation(Long id) throws WrongPersonException {
        Person person = ((Session) context.getAttribute("session")).getPerson();
        EatCalendar eatCalendar = eatCalendarRepository.getById(id);
        long personId = eatCalendar.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("INVALID PERSON!");
        }
        eatCalendarRepository.deleteById(id);
    }

    public void updateRecommendation(EatCalendarDTO eatCalendarDTO) throws WrongPersonException, InvalidDataException {
        if (eatCalendarDTO.getInfo() == null || eatCalendarDTO.getInfo().equals("")) {
            throw new InvalidDataException("Рекомендация не может быть пустой!");
        }
        Person person = ((Session) context.getAttribute("session")).getPerson();
        EatCalendar eatCalendar = eatCalendarRepository.getById(eatCalendarDTO.getId());
        long personId = eatCalendar.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("INVALID PERSON!");
        }
        eatCalendarRepository.updateRecommendation(eatCalendarDTO.getId(), eatCalendar.getDate(), eatCalendarDTO.getInfo());
    }
}
