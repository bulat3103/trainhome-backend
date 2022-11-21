package com.example.trainhome.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
public class TrainingDTO {
    private Date trainingDate;
    private String link;
    @NonNull
    private Long groupId;
}
