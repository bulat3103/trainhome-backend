package com.example.trainhome.validators;

import com.example.trainhome.dto.EatCalendarDTO;
import com.example.trainhome.exceptions.InvalidDataException;

import java.sql.Date;
import java.time.LocalDate;

public class EatCalendarRequestValidator {
    public static void validateCreateRequest(EatCalendarDTO dto) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (dto.getDate().before(Date.valueOf(LocalDate.now()))) {
            message.append("Рекомендация не может назначаться на прошедшую дату!");
            valid = false;
        }
        if (dto.getInfo() == null || dto.getInfo().equals("")) {
            message.append("Рекомендация не может быть пустой!");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }
}
