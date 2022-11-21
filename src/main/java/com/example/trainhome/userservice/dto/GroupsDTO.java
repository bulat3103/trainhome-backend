package com.example.trainhome.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupsDTO {
    private String name;
    private String sportSphereName;
    private Integer maxCount;
    private Integer trainsLeft;
}