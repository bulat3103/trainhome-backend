package com.example.trainhome.dto;

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
}
