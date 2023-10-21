package com.example.trainhome.validators;

import com.example.trainhome.dto.AuthRequestDTO;
import com.example.trainhome.dto.RegisterRequestDTO;
import com.example.trainhome.dto.SportPriceDTO;
import com.example.trainhome.exceptions.InvalidDataException;

import java.sql.Date;
import java.time.LocalDate;

public class AuthRequestValidator {
    public static void validateRegister(String role, RegisterRequestDTO dto) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (role == null || (!role.equals("ROLE_CLIENT") && !role.equals("ROLE_COACH"))) {
            message.append("Неправильное указание роли!");
            valid = false;
        }
        if (dto.getPassword() == null || dto.getPassword().equals("") || dto.getPassword().length() < 5) {
            message.append("Пароль должен состоять минимум из 5 символов!");
            valid = false;
        }
        if (dto.getName() == null || dto.getName().equals("")) {
            message.append("ФИО не может быть пустым!");
            valid = false;
        }
        if (dto.getBirthday() == null || dto.getBirthday().after(Date.valueOf(LocalDate.now()))) {
            message.append("Некорректная дата рождения!");
            valid = false;
        }
        if (dto.getListPrices() != null) {
            for (SportPriceDTO priceDto : dto.getListPrices()) {
                if (priceDto.getPrice() <= 0) {
                    message.append("Стоимость занятий должна быть положительным числом!");
                    valid = false;
                }
            }
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }

    public static void validateLogin(AuthRequestDTO dto) throws InvalidDataException {
        StringBuilder message = new StringBuilder();
        boolean valid = true;
        if (dto.getEmail() == null || dto.getEmail().equals("")) {
            message.append("Email не может быть пустым!");
            valid = false;
        }
        if (dto.getPassword() == null || dto.getPassword().equals("")) {
            message.append("Пароль не может быть пустым!");
            valid = false;
        }
        if (!valid) throw new InvalidDataException(message.toString());
    }
}
