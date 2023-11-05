package com.example.trainhome.validators;

import com.example.trainhome.dto.GroupsDTO;
import com.example.trainhome.exceptions.InvalidDataException;

public class GroupsRequestValidator {
    public static void validateCreateRequest(GroupsDTO dto) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (dto.getName() == null || dto.getName().equals("")) {
            message.append("Название не может быть пустым!\n");
            valid = false;
        }
        if (dto.getMaxCount() == null || dto.getMaxCount() <= 0) {
            message.append("Количество людей должно быть положительным!\n");
            valid = false;
        }
        if (dto.getTrainsLeft() == null || dto.getTrainsLeft() <= 0) {
            message.append("количество тренировок должно быть положительным!\n");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }
}
