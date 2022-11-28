package com.example.trainhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class EatCalendarDTO {
    private Long id;
    private String info;
    private Date date;
    private Long personId;
    private PersonDTO coachDTO;
}
