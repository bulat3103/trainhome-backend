package com.example.trainhome.services;

import com.example.trainhome.dto.EatCalendarDTO;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.exceptions.RecommendationNotFoundException;
import com.example.trainhome.exceptions.WrongPersonException;
import com.example.trainhome.repositories.EatCalendarRepository;
import com.example.trainhome.repositories.PersonRepository;
import com.example.trainhome.entities.EatCalendar;
import com.example.trainhome.entities.Person;
import com.example.trainhome.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        EatCalendar recommendation = eatCalendarRepository.getByPersonIdAndCoachIdAndDate(person.getId(), personId, date);
        if (recommendation == null) {
            throw new RecommendationNotFoundException("Не было найдено записей!");
        }
        return new EatCalendarDTO(recommendation.getId(), recommendation.getInfo(), recommendation.getDate(),
                personId, null);
    }

    public List<Date> getPersonDates() {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return eatCalendarRepository.getAllDate(person.getId());
    }

    public List<EatCalendarDTO> getPersonRecommendationByDate(Long id, Date date) {
        List<EatCalendar> list = eatCalendarRepository.getAllByPersonIdAndDate(id, date);
        List<EatCalendarDTO> listToReturn = new ArrayList<>();
        for (EatCalendar recommendation : list) {
            listToReturn.add(new EatCalendarDTO(recommendation.getId(), recommendation.getInfo(), recommendation.getDate(),
                    id, PersonDTO.PersonToPersonDTO(recommendation.getCoachId().getPersonId())));
        }
        return listToReturn;
    }

    public Long createRecommendation(EatCalendarDTO eatCalendarDTO) {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        EatCalendar eatCalendar = new EatCalendar(
                eatCalendarDTO.getInfo(),
                eatCalendarDTO.getDate(),
                personRepository.getById(eatCalendarDTO.getPersonId()),
                coachRepository.getById(person.getId())
        );
        eatCalendarRepository.save(eatCalendar);
        return eatCalendar.getId();
    }

    public void deleteRecommendation(Long id) throws WrongPersonException {
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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
        Person person = personRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        EatCalendar eatCalendar = eatCalendarRepository.getById(eatCalendarDTO.getId());
        long personId = eatCalendar.getCoachId().getPersonId().getId();
        if (personId != person.getId()) {
            throw new WrongPersonException("INVALID PERSON!");
        }
        eatCalendarRepository.updateRecommendation(eatCalendarDTO.getId(), eatCalendar.getDate(), eatCalendarDTO.getInfo());
    }
}
