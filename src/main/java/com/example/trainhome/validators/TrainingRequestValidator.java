package com.example.trainhome.validators;

import com.example.trainhome.dto.TrainingDTO;
import com.example.trainhome.exceptions.InvalidDataException;

import java.sql.Date;
import java.time.LocalDate;

public class TrainingRequestValidator {
    public static void validateCreateRequest(TrainingDTO dto) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (dto.getTrainingDate().before(Date.valueOf(LocalDate.now()))) {
            message.append("Тренировка не может назначаться на прошедшую дату!");
            valid = false;
        }
        if (dto.getLink() == null || dto.getLink().equals("")) {
            message.append("Ссылка на тренировку не может быть пустой!");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }
}
