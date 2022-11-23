package com.example.trainhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class TransactionsDTO {
    private Date date;
    private Long coachId;
    private Integer money;
}
