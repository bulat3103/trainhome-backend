package com.example.trainhome.validators;

import com.example.trainhome.dto.TransactionsDTO;
import com.example.trainhome.exceptions.InvalidDataException;

public class TransactionsRequestValidator {
    public static void validateCreateTransaction(TransactionsDTO dto) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (dto.getCoachId() == null) {
            message.append("CoachId не может быть пустым!");
            valid = false;
        }
        if (dto.getMoney() == null) {
            message.append("Money не может быть пустым!");
            valid = false;
        }
        if (dto.getDate() == null) {
            message.append("Date не может быть пустым!");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }
}
