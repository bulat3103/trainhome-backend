package com.example.trainhome.userservice.services;

import com.example.trainhome.userservice.dto.GroupsDTO;
import com.example.trainhome.userservice.entities.Groups;
import com.example.trainhome.userservice.repositories.CoachRepository;
import com.example.trainhome.userservice.repositories.GroupsRepository;
import com.example.trainhome.userservice.repositories.SportSphereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsService {
    @Autowired
    private SportSphereRepository sportSphereRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    public boolean validateGroup(GroupsDTO groupsDTO) {
        if (groupsDTO.getName() == null || groupsDTO.getName().equals("")) return false;
        if (sportSphereRepository.getByName(groupsDTO.getSportSphereName()) == null) return false;
        if (groupsDTO.getCount() == null || groupsDTO.getCount() <= 0) return false;
        if (groupsDTO.getMaxCount() == null || groupsDTO.getMaxCount() <= 0 ||
                groupsDTO.getMaxCount() < groupsDTO.getCount()) return false;
        if (!coachRepository.existsById(groupsDTO.getCoachId())) return false;
        return groupsDTO.getTrainsLeft() != null && groupsDTO.getTrainsLeft() > 0;
    }

    public void addNewGroup(GroupsDTO groupsDTO) {
        groupsRepository.save(new Groups(
                groupsDTO.getName(),
                coachRepository.getByPersonId(groupsDTO.getCoachId()),
                sportSphereRepository.getByName(groupsDTO.getSportSphereName()),
                groupsDTO.getCount(),
                groupsDTO.getMaxCount(),
                groupsDTO.getTrainsLeft()
        ));
    }
}
