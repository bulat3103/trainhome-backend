package com.example.trainhome.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoachSearchDTO {
    private List<String> sports;
    private Integer minPrice;
    private Integer maxPrice;
    private Double minRating;
    private Double maxRating;
}
