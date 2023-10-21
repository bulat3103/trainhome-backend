package com.example.trainhome.validators;

import com.example.trainhome.dto.CoachSearchDTO;
import com.example.trainhome.exceptions.InvalidDataException;

public class CoachRequestValidator {
    public static void validateCoachDto(CoachSearchDTO dto) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (dto.getMinRating() != null && dto.getMinRating() > 5) {
            message.append("Рейтинг не может быть больше 5!");
            valid = false;
        }
        if (dto.getMaxRating() != null && dto.getMaxRating() > 5) {
            message.append("Рейтинг не может быть больше 5!");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }
}
