package com.example.trainhome.dto;

import com.example.trainhome.entities.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupsDTO {
    private Long id;
    private String name;
    private PersonDTO coach;
    private String sportSphereName;
    private Integer maxCount;
    private Integer trainsLeft;

    public static GroupsDTO GroupsToGroupsDTO(Groups groups) {
        return new GroupsDTO(groups.getId(), groups.getName(), PersonDTO.PersonToPersonDTO(groups.getCoachId().getPersonId()),
                groups.getSportSphereId().getName(), groups.getMaxCount(), groups.getTrainsLeft());
    }
}
