package com.example.trainhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditDTO {
    private Integer commonCountMonths;
    private Integer money;
    private Long personId;
    private Long coachId;
}
