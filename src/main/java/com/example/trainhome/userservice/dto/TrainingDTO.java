package com.example.trainhome.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
public class TrainingDTO {
    private Long id;
    private Date trainingDate;
    private PersonDTO personDTO;
    private String link;
    @NonNull
    private Long groupId;
}
