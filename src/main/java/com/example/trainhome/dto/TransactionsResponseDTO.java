package com.example.trainhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class TransactionsResponseDTO {
    private Date date;
    private Integer money;
    private PersonDTO personDTO;
}
