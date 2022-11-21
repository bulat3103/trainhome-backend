package com.example.trainhome.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupsDTO {
    private String name;
    private Long coachId;
    private String sportSphereName;
    private Integer maxCount;
    private Integer count;
    private Integer trainsLeft;
}
