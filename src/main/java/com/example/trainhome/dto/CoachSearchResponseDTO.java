package com.example.trainhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachSearchResponseDTO {
    private PersonDTO personDTO;
    private Double rating;
    private String achievements;
    private String sportSphereName;
    private Integer sportSpherePrice;
}
