package com.example.trainhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TrainingDTO {
    private Long id;
    private Date trainingDate;
    private PersonDTO personDTO;
    private String link;
    private Long createGroupId;
    private GroupsDTO groupsDTO;
}
