package com.example.trainhome.services;

import com.example.trainhome.dto.CoachSearchDTO;
import com.example.trainhome.dto.CoachSearchResponseDTO;
import com.example.trainhome.dto.PersonDTO;
import com.example.trainhome.entities.Coach;
import com.example.trainhome.entities.SportSphere;
import com.example.trainhome.entities.SportSphereCoachPrice;
import com.example.trainhome.repositories.CoachRepository;
import com.example.trainhome.repositories.PersonRepository;
import com.example.trainhome.repositories.SportSphereCoachPriceRepository;
import com.example.trainhome.repositories.SportSphereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SportSphereCoachPriceRepository sportSphereCoachPriceRepository;

    @Autowired
    private SportSphereRepository sportSphereRepository;

    public List<CoachSearchResponseDTO> getFilteredCoaches(CoachSearchDTO coachSearchDTO) {
        List<CoachSearchResponseDTO> toReturn = new ArrayList<>();
        for (String name : coachSearchDTO.getSports()) {
            SportSphere sportSphere = sportSphereRepository.getByName(name);
            List<SportSphereCoachPrice> list = sportSphereCoachPriceRepository.getByNameAndPrice(sportSphere.getId(),
                    coachSearchDTO.getMinPrice(), coachSearchDTO.getMaxPrice());
            for (SportSphereCoachPrice item : list) {
                Coach coach = coachRepository.getById(item.getId().getCoachId().getPersonId().getId());
                if (coach.getRating() >= coachSearchDTO.getMinRating() && coach.getRating() <= coachSearchDTO.getMaxRating()) {
                    toReturn.add(new CoachSearchResponseDTO(
                            PersonDTO.PersonToPersonDTO(personRepository.getById(coach.getPersonId().getId())),
                            coach.getRating(),
                            coach.getAchievements(),
                            name,
                            item.getPrice()
                    ));
                }
            }
        }
        return toReturn;
    }
}
