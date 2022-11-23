package com.example.trainhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupsDTO {
    private Long id;
    private String name;
    private String sportSphereName;
    private Integer maxCount;
    private Integer trainsLeft;
}
